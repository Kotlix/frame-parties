package ru.kotlix.frame.parties.server.mapper

import ru.kotlix.frame.auth.api.token.dto.UserInfo
import ru.kotlix.frame.parties.server.service.dto.UserInfo as ServiceUserInfo

fun UserInfo.toServiceUserInfo() =
    ServiceUserInfo(
        id = id,
        login = login,
        username = username,
    )
