package com.ktzy.notetaker

import java.time.LocalDateTime

class Note{
    var id: Int? = null
    var title: String
    var content: String
    lateinit var dateCreated: LocalDateTime

    constructor(id: Int, title: String, content: String, dateCreated: LocalDateTime){
        this.id = id
        this.title = title
        this.content = content
        this.dateCreated = dateCreated
    }

    constructor(title: String, content: String){
        this.title = title
        this.content = content
    }
}