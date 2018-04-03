(ns democracy-clojure.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.reload :refer [wrap-reload]]
            [democracy-clojure.home :as home]
            [democracy-clojure.search :as search]))

(defroutes app
  (GET "/" [] home/page)
  ; Post to the /search route, extracting the city and state params (which are the only things we care about atm)
  (POST "/search" [city state] (search/search city state))
  (route/resources "/")
  (route/not-found "Not found"))

(def handler
  (-> app
      (wrap-defaults site-defaults)
      wrap-reload))
