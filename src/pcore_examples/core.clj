(ns pcore-examples.core
  (:gen-class)
  (:import [com.puppet.pcore Pcore]
           [com.puppet.pcore.serialization SerializationFactory]
           [java.util.function Supplier]
           [java.io BufferedInputStream]
           [java.io FileInputStream]
           [com.fasterxml.jackson.databind ObjectMapper]
           (java.util List HashMap)))

(use 'clojure.walk)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def mapper (ObjectMapper.))

(defn declare-type [name type-string]
  (.declareType (Pcore/typeEvaluator) name type-string))

(defn resolve-type [type-string]
  (.resolve (Pcore/typeEvaluator) type-string))

(defn with-local-scope
  [func & args]
  (Pcore/withLocalScope (reify Supplier (get [this] (apply func args)))))

(defn chunk-deserializer
  []
  (.forInputChunks (Pcore/serializationFactory SerializationFactory/JSON)))

(defn buffered-input
  [path]
  (BufferedInputStream. (FileInputStream. path)))

(defn parse-json
  [path]
  (keywordize-keys
    (.readValue mapper
       (buffered-input path) HashMap)))
