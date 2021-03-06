(defproject todo-clj "0.1.0-SNAPSHOT"
  :description "TODO管理REST API"
  :url "https://github.com/simonNozaki/todo-clj.git"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [ring "1.9.5"]
                 [ring/ring-json "0.5.1"]
                 [ring-logger "1.1.1"]
                 [compojure "1.6.2"]]
  :repl-options {:init-ns todo-clj.core})
