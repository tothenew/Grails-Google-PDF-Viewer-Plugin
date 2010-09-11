class UrlMappings {

	static mappings = {
		"/$controller/$action?/$uniqueId?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
