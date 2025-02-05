package ru.kotlix.frame.parties.server.service.dto

import ru.kotlix.frame.auth.api.token.dto.UserInfo
import ru.kotlix.frame.parties.server.service.dto.UserInfo as ServerUserInfo

fun UserInfo.toServerUserInfo() =
    ServerUserInfo(
        id = id,
        login = login,
        username = username,
    )
