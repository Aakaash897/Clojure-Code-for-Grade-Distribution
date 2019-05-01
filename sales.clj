(require ['clojure.string :as 'str])
(ns sales
   (:gen-class))

(def cust-map (atom (sorted-map)))
(def prod-map (atom (sorted-map)))
(def sales-map (atom (sorted-map)))  
(def calc-map (atom (sorted-map)))


(defn smap []
	(doseq [temp (clojure.string/split-lines (slurp "cust.txt"))]
	(swap! cust-map assoc (subs temp 0 1) (clojure.string/split temp #"\|"))
	)
	(doseq [temp (clojure.string/split-lines (slurp "prod.txt"))]
    (swap! prod-map assoc (subs temp 0 1) (clojure.string/split temp #"\|"))
	)
	(doseq [temp (clojure.string/split-lines (slurp "sales.txt"))]
	(let [[sID cID pID itemCount] (clojure.string/split temp #"\|")]
	(swap! calc-map assoc sID {:cname (clojure.string/lower-case(nth (@cust-map cID) 1)) :pname (clojure.string/lower-case(nth (@prod-map pID) 1)) :expense (nth (@prod-map pID) 2) :number itemCount :itemprice (* (read-string (nth (@prod-map pID) 2)) (read-string itemCount))})
	)
	)
	)

(defn optionone []
	(println "First Option")
	(doseq [temp (clojure.string/split-lines (slurp "cust.txt"))]
	(swap! cust-map assoc (subs temp 0 1) (clojure.string/split temp #"\|"))
	)
	(doseq [[k v] @cust-map] (println (str k ":" v)))
    )
	
(defn optiontwo []
	(println "Second Option")
	(doseq [temp (clojure.string/split-lines (slurp "prod.txt"))]
    (swap! prod-map assoc (subs temp 0 1) (clojure.string/split temp #"\|"))
	)
	(doseq [[k v] @prod-map] (println (str k ":" v)))
    )
	
(defn optionthree []	
	(println "Third Option")
	(doseq [temp (clojure.string/split-lines (slurp "sales.txt"))]
	(let [[sID cID pID ] (clojure.string/split temp #"\|")]
	(swap! sales-map assoc sID [(nth (@cust-map cID) 1) (nth (@prod-map pID) 1) (nth (@prod-map pID) 2)])
	)
	)
	(doseq [[k v] @sales-map] (println (str k ":" v)))
	)
	
(defn optionfour []
	(println "Fourth Option")
	(println "Enter Name of the Customer:")
	(let [name (read-line)]
		(println name ": $" (apply + (map #(% :itemprice) (filter (comp #{clojure.string/lower-case name}:cname ) (vals @calc-map)))))
	)
	)	
	
(defn optionfive []
	(println "Fifth Option")
	(println "Enter the name of the Product :")
	(let [name (read-line)]
	(println name ":" (apply + (map read-string(map #(% :number) (filter (comp #{clojure.string/lower-case name} :pname ) (vals @calc-map))))))
	)
	)
	

(smap)
(optionone)
(optiontwo)
(optionthree)
(optionfour)
(optionfive)
(optionfive)
(defn mainmenu []
	(println "Function Choices")
	(println "1. Display Customer Table")
	(println "2. Display Product Table")
	(println "3. Display Sales Table")
	(println "4. Total Sales for Customer")
	(println "5. Total Count for Product")
	(println "6. Exit")
	(println "Enter an option?")
	(let [optionval (read-line)]
	(if (= optionval 1)
		(println "hello")
	)
	)
	)
(mainmenu)