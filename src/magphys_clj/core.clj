(ns magphys-clj.core
    (:use [incanter.core]))

(defn cosmological_constant [h omega omega_lambda]
        (/ omega_lambda (* 3 (pow h 2))))

(defn q [cosmological_constant omega omega_lambda]
             (if (= omega_lambda 0)
             (/ omega 2.0)
                 (- (* 3 (/ omega 2.0) 1))))
