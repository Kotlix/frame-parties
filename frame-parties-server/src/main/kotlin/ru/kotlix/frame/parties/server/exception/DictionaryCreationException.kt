package ru.kotlix.frame.parties.server.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class DictionaryCreationException(message: String) : RuntimeException(message)