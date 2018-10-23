(defn constant [x] (fn [args] x))

(defn variable [n] (fn [args] (get args n)))

(defn binaryFunction [k] (fn [l, r] (fn [args] (k (l args) (r args)))))

(defn unaryFunction [k] (fn [l] (fn [args] (k (l args)))))

(def m{"add" + "subtract" -})

(defmacro fn-name [f] `(-> ~f var meta :name str))

(def add (binaryFunction (fn [l, r] ((get m (fn-name add)) l r))))

(def subtract (binaryFunction (fn [l, r] ((get m (fn-name subtract)) l r))))

(def multiply (binaryFunction (fn [l, r] (* l r))))

(def divide (binaryFunction (fn [l, r] (/ l (double r)))))

(def negate (unaryFunction (fn [l] (* l -1))))

(def sinh (unaryFunction (fn [l] (Math/sinh l))))

(def cosh (unaryFunction (fn [l] (Math/cosh l))))

(def oper {"+" add "-" subtract "/" divide "*" multiply "negate" negate "sinh" sinh "cosh" cosh})

(defn Op [str] (fn [args] (cond
                            (number? str) str
                            (symbol? str) (get args (name str))
                            :else (if (= 3 (count str))
                             (((get oper (name (first str))) (constant ((Op (nth str 1)) args)) (constant  ((Op (nth str 2)) args))) args)
                             (((get oper (name (first str))) (constant  ((Op (nth str 1)) args))) args)))))

(defn parseFunction [str] (fn [args] (cond
                                       (number? (read-string str)) (read-string str)
                                       (symbol? (read-string str)) (get args (name (read-string str)))
                                       :else ((Op (read-string str)) args))))

;(def expr (parseFunction "10.0"))
;(def expr (parseFunction "x"))
;(def expr (parseFunction "(negate 3.0)"))
;(def expr (parseFunction "(/ (negate x) 0.0)"))
(def expr (subtract (variable "x") (constant 2.0)))

(println (expr {"x" 1.0 "y" 0.0 "z" 0.0}))