(ns magphys-clj.core
    (:require [clojure.string :as str])
    (:use [incanter.core])
    )

(defn cosmological_constant [h omega omega_lambda]
        (/ omega_lambda (* 3 (pow h 2))))

(defn q [cosmological_constant omega omega_lambda]
             (if (= omega_lambda 0)
             (/ omega 2.0)
                 (- (* 3 (/ omega 2.0) 1))))

(defrecord Filter [name lambda_eff id])

(defn read-file [file]
	(map #(let [x (str/split % #"\t\s")] (Filter. (first x) (Float. (second x)) (Integer. (str/trim (nth x 2))))) (rest (str/split-lines (slurp file))))
)

