;; utility functions

(defn get-match [regex]
    (fn [input]
	 	(let [match (re-matches regex input)]
	 		[(get match 1)(get match 2)])))

(defn maybe [value func]
	(if (= nil value)
		nil
		(func value)))

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
	(get-match #"^(\d+)((.|\n)*)$"))

(def match-string
	(get-match #"^(\"[^\\']*(?:\\.[^\\']*)*\")((.|\n)*)$"))

(def match-bool
	(get-match #"^(true|false)((.|\n)*)$"))

(def match-null
	(get-match #"^(null)((.|\n)*)$"))


;; basic parsers

(def space-parser
	(get-match #"^([ \t]+)((.|\n)*)$"))

(def open-sqr-bkt
	(get-match #"^(\[)((.|\n)*)$"))

(def close-sqr-bkt
	(get-match #"^(\])((.|\n)*)$"))

(def open-curly-bkt
	(get-match #"^(\{)((.|\n)*)$"))

(def close-curly-bkt
	(get-match #"^(\{)((.|\n)*)$"))

;; value parsers

(defn number-parser [input]
	(maybe (match-number input) get-number))

(defn string-parser [input]
	(maybe (match-string input) get-string))

(defn bool-parser [input]
	(maybe (match-bool input) get-bool))

(defn null-parser [input]
	(maybe (match-null input) get-null))
