(defproject ach-api "0.1.0-SNAPSHOT"
  :description "AtCoder Choice"
  :url "http://example.com/FIXME"
  :license "MIT License"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [bidi "2.1.6"]
                 [hiccup "1.0.5"]
                 [ring "1.9.1"]
                 [camel-snake-kebab "0.4.2"]]
  :plugins [[cider/cider-nrepl "0.25.9"]
            [refactor-nrepl "2.5.1"]]
  :main ^:skip-aot ach-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
