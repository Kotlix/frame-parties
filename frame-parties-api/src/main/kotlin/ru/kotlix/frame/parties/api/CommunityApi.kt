package ru.kotlix.frame.parties.api

import ru.kotlix.frame.parties.api.dto.*
import ru.kotlix.frame.parties.api.dto.entities.*
import ru.kotlix.frame.parties.api.dto.oldrequests.*

typealias CommunityId = Int
typealias Token = String
typealias RoleId = Int
typealias ChatId = Int
typealias ChannelId = Int
typealias VoiceChatId = Int
typealias CatalogId = Int

interface CommunityApi {
//    fun createCommunity(request: CreateCommunityRequest) : CommunityId
//
//    fun deleteCommunity(id: Int)
//
//    fun getCommunityInfo(id: Int) : Community
//
//    fun getAllCommunities() : List<CommunityInfoLight> // of a user
//
//    fun getCommunityByName(request: GetCommunityByNameRequest) : CommunityInfoLight
//
//    fun joinCommunityById(id: Int)
//
//    fun joinCommunityByToken(token: String)
//
//    fun leaveCommunityById(id: Int)
//
//    fun createToken(request: CreateCommunityTokenRequest) : Token
//
//    fun changeCommunityInfo(request: ChangeCommunityInfoRequest)
//
//    fun createRole(request: CreateRoleRequest) : RoleId
//
//    fun editRole(request: EditRoleRequest)
//
//    fun assignRole(request: AssignRoleRequest)
//
//    fun createChat(request: CreateChatRequest) : ChatId
//
//    fun createChannel(request: CreateChannelRequest) : ChannelId
//
//    fun createVoiceChat(request: CreateVoiceChatRequest) : VoiceChatId
//
//    fun createCatalog(request: CreateCatalogRequest) : CatalogId
//
//    fun editChat(request: EditChatRequest)
//
//    fun editChannel(request: EditChannelRequest)
//
//    fun editVoiceChat(request: EditVoiceChatRequest)
//
//    fun deleteChat(request: DeleteChatRequest)
//
//    fun deleteChannel(request: DeleteChannelRequest)
//
//    fun deleteVoiceChat(request: DeleteVoiceChatRequest)
//
//    fun deleteCatalog(request: DeleteCatalogRequest)
//
//    fun getChatInfo(request: GetChatInfoRequest) : Chat
//
//    fun getChannelInfo(request: GetChannelInfoRequest) : Channel
//
//    fun getVoiceChatInfo(request: GetVoiceChatInfoRequest) : VoiceChat
//
//    fun getCommunityCatalog(request: GetCommunityCatalogRequest)
//
//    fun sendMessage(request: SendMessageRequest)
//
//    fun getMessages(request: GetMessagesRequest) : List<Message>
//
//    fun connectVoiceChat(request: ConnectVoiceChatRequest)
//
//    fun disconnectVoiceChat(request: DisconnectVoiceChatRequest)
}