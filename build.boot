(set-env!
 :source-paths #{"sass" "dev" "src" "resources"}
 :asset-paths #{"assets"}
 :dependencies '[[adzerk/boot-cljs "1.7.48-5" :scope "test"]
                 [adzerk/boot-cljs-repl "0.2.0" :scope "test"]
                 [adzerk/boot-reload "0.4.0" :scope "test"]
                 [hiccup "1.0.5"]
                 [cljsjs/react "0.14.3-0"]
                 [cljs-http "0.1.37"]
                 [mathias/boot-sassc "0.1.5" :scope "test"]
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.122"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [pandeiro/boot-http "0.6.3" :scope "test"]
                 [clj-http "2.0.0"]
                 [reagent "0.6.0-alpha"]
                 [rum "0.6.0"]
                 [cheshire "5.5.0"]
                 [stencil "0.5.0"]
                 [org.clojure/tools.nrepl "0.2.12"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[adzerk.boot-reload    :refer [reload]]
         '[clojure.java.shell :as sh]
         '[mathias.boot-sassc :refer [sass]]
         '[pandeiro.boot-http :refer [serve]])

(deftask dev
  "Simple alias to run application in development mode"
  []
  (set-env! :target-path "target/dev")
  (comp
   ;;(serve :dir "target/dev" :port 3001)
   (watch)
   (speak)
   (sass :sass-file "app.scss"
         :output-dir "."
         :line-numbers true
         :source-maps true)
   (reload :on-jsload 'edge.main/init)
   (cljs-repl)
   (cljs :ids #{"edge"} :optimizations :none)))

(deftask build []
  (set-env! :target-path "target/prod")
  (comp
   (sass :sass-file "app.scss"
         :output-style "compressed"
         :output-dir "."
         :line-numbers false
         :source-maps false)
   (cljs :ids #{"edge"} :optimizations :advanced)))

