package ru.kotlix.frame.parties.api

import ru.kotlix.frame.parties.api.dto.*
import ru.kotlix.frame.parties.api.dto.entities.*

typealias CommunityId = String
typealias Id = String
typealias Token = String
typealias RoleId = String
typealias ChatId = String
typealias ChannelId = String
typealias VoiceChatId = String

interface CommunityApi {
    fun createCommunity(request: CreateCommunityRequest) : CommunityId

    fun deleteCommunity(id: String)

    fun getCommunityInfo(id: String) : Community

    fun getAllCommunities() : List<CommunityInfoLight> // of a user

    fun getCommunityByName(request: GetCommunityByNameRequest) : CommunityInfoLight

    fun joinCommunityById(id: String)

    fun joinCommunityByToken(token: String)

    fun leaveCommunityById(id: String)

    fun createToken(request: CreateCommunityTokenRequest) : Token

    fun changeCommunityInfo(request: ChangeCommunityInfoRequest)

    fun createRole(request: CreateRoleRequest) : RoleId

    fun editRole(request: EditRoleRequest)

    fun assignRole(request: AssignRoleRequest)

    fun createChat(request: CreateChatRequest) : ChatId

    fun createChannel(request: CreateChannelRequest) : ChannelId

    fun createVoiceChat(request: CreateVoiceChatRequest) : VoiceChatId

    fun editChat(request: EditChatRequest)

    fun editChannel(request: EditChannelRequest)

    fun editVoiceChat(request: EditVoiceChatRequest)

    fun deleteChat(request: DeleteChatRequest)

    fun deleteChannel(request: DeleteChannelRequest)

    fun deleteVoiceChat(request: DeleteVoiceChatRequest)

    fun getChatInfo(request: GetChatInfoRequest) : Chat

    fun getChannelInfo(request: GetChannelInfoRequest) : Channel

    fun getVoiceChatInfo(request: GetVoiceChatInfoRequest) : VoiceChat

    fun getCommunityCatalog(request: GetCommunityCatalogRequest)

    fun sendMessage(request: SendMessageRequest)

    fun getMessages(request: GetMessagesRequest) : List<Message>

    fun connectVoiceChat(request: ConnectVoiceChatRequest)

    fun disconnectVoiceChat(request: DisconnectVoiceChatRequest)
}