(defproject atcode-choice "0.1.0-SNAPSHOT"
  :description "A Tool for AtCoder"
  :url "http://example.com/FIXME"
  :license "Eclipse Public License 2.0"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [clj-http "3.12.0"]
                 [ring "1.9.1"]
                 [metosin/ring-http-response "0.9.2"]
                 [integrant "0.8.0"]
                 [cheshire "5.10.0"]
                 [environ "1.2.0"]
                 [bidi "2.1.6"]
                 [hiccup "1.0.5"]
                 [camel-snake-kebab "0.4.2"]]
  :plugins [[cider/cider-nrepl "0.25.9"]
            [refactor-nrepl "2.5.1"]]
  :main ^:skip-aot ach-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev [:project/dev :profiles/dev]
             :profiles/dev {}
             :project/dev {:source-paths ["src"]}})
