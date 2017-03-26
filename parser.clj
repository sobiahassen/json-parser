;; constants

;; regexes
(def number-regex          #"^(\d+)((.|\n)*)$")
(def string-regex          #"^(\"[^\\']*(?:\\.[^\\']*)*\")((.|\n)*)$")
(def bool-regex            #"^(true|false)((.|\n)*)$")
(def null-regex            #"^(null)((.|\n)*)$")
(def space-regex           #"^([ \t]+)((.|\n)*)$")
(def open-sqr-regex        #"^(\[)((.|\n)*)$")
(def close-sqr-regex       #"^(\])((.|\n)*)$")
(def open-curly-regex      #"^(\{)((.|\n)*)$")
(def close-curly-regex     #"^(\{)((.|\n)*)$")

;; parser vector for the value parser
(def parsers [number-parser string-parser bool-parser]) ;; add array parser and value parser

;; utility functions

(defn get-match [regex]
    (fn [input]
	 	(let [match (re-matches regex input)]
	 		[(get match 1)(get match 2)])))

(defn maybe [value func]
	(if (= nil value)
		nil
		(func value)))

(defn get-first-match [input]
	(filter #(not (= nil)) (map #(% input) parsers)))

;; value extractors 

(defn get-number [value]
    (update value 0 #(Integer/parseInt %)))

(defn get-string [value]
	(update value 0 #(str %)))

(defn get-bool [value]
	(not (= "false" value)))

(defn get-null [value]
	(update value 0 #(symbol %)))

;; matcher functions

(def match-number
	(get-match number-regex))

(def match-string
	(get-match string-regex))

(def match-bool
	(get-match bool-regex))

(def match-null
	(get-match null-regex))

;; basic parsers

(def space-parser
	(get-match space-regex))

(def open-sqr-bkt
	(get-match open-sqr-regex))

(def close-sqr-bkt
	(get-match close-sqr-regex

(def open-curly-bkt
	(get-match open-curly-regex

(def close-curly-bkt
	(get-match close-curly-regex)

(def comma-parser)
;; value parsers

(defn number-parser [input]
	(maybe (match-number input) get-number))

(defn string-parser [input]
	(maybe (match-string input) get-string))

(defn bool-parser [input]
	(maybe (match-bool input) get-bool))

(defn null-parser [input]
	(maybe (match-null input) get-null))

;; parser-array for the value-parser

(defn value-parser [input]
	(get-first-match input))

