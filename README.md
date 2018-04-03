Well, obviously, I don't know clojure. So I spent 2 hours working on this, but that was also a lot of
time learning clojure as I was going along.

So there's lots of stuff that can be done going forward -

* The results page is minimalistic, and I'm being very charitable in calling it that. It could display
  additional related info out of the JSON file, prettily format things, and do things like put the polling
  place location onto a map (and maybe even get really fancy and wire in automatic directions and travel times)

* There's no error handling, so if there are no upcoming elections - you just get a blank page.

* There are no tests. I'm not even aware of the libraries/idioms that clojure uses for testing.

* Getting more OCD division IDs would be a matter of wiring into something like the USPS's address/zipcode lookup
  and getting more info there. As of right now, the search form only pays any attention to the city and state
  and completely throws out everything else.

* I'm unfamiliar with the EDN format, so just ran it as JSON. Presumably, to make it more clojure-ish, using
  EDN natively would be better.
