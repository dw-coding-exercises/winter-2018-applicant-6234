(ns democracy-clojure.search
  (:require
    [hiccup.page :refer [html5]]
    [clj-http.client :as client]
    [clojure.string :as string]
))

; standard header that just spits out the same content as on the home page
(defn header []
  [:head
   [:meta {:charset "UTF-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1.0, maximum-scale=1.0"}]
   [:title "Find my next election"]
   [:link {:rel "stylesheet" :href "default.css"}]])

; This actually outputs the elections. Note that if there are no upcoming elections, it doesn't say so -
; just "Upcoming elections" and then nothing. Not very user friendly.
(defn output [divisions]
  [:div
   [:h1 "Upcoming elections"]
   ; just iterate over the divisiosn and yank out the description and date and display them.
   ; the date should be reformatted into a user friendly string.
   (for [d divisions] [:div (get d :description) " at " (get d :date)])
  ]
)

(defn search [city state]

  ; build up a URL to the turbovote API - this one is real simple, only using the state and city ocd-divisions
  ; the sanitization of city and state is sloppy - it just takes any non-word character and turns it into an underscore.
  ; This is specifically to handle the case of spaces - "West Miami" should be "West_Miami". But I wired it up to correct
  ; any other non-words for url handling purposes. This should be changed to something more robust, preferably url escaping
  ; it all.
  (def url
    (string/join ""
      ["https://api.turbovote.org/elections/upcoming?district-divisions=ocd-division/country:us/state:"
      (string/lower-case (string/replace state #"\W" "_"))
      "/place:"
      (string/lower-case (string/replace city #"\W" "_"))
      ]
    )
  )

  ; actually make our http request, and get back the results as json
  (def response
        (client/get
          url
          {:headers { "accept" "application/json" } :as :json }
        )
  )
  ; yank out our list of divisions from the body
  (def divisions (get response :body))

  (html5
   (header)
   ; and then just hand the divisions on to our output
   ( output divisions )
  )


)
