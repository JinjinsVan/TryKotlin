package my.code.trykotlin.http

import java.io.IOException

/**
 * Created by Jins on 2018/4/25.
 */
class ApiException : IOException {

    var code: Int? = null
    override var message: String? = null

    constructor(code: Int, message: String) {
        this.code = code
        this.message = message
    }

}