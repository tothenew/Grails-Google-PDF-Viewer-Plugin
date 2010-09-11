package pdfviewer

class GoogleDocPdf {
    String path
    String uniqueId
    Date dateCreated
       

    static constraints = {
        path(nullable:false)
        uniqueId(nullable:false)
    }
}
