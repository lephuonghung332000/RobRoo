package net.mindzone.robroo.data.utils
import net.mindzone.robroo.data.ResponseCode
import net.mindzone.robroo.data.applicationSetting.entity.ApplicationResponse
import net.mindzone.robroo.data.faqArticle.entity.FaqArticleResponse
import net.mindzone.robroo.data.faqList.entity.FaqListResponse
import net.mindzone.robroo.data.forumArticle.entity.comment.ForumArticleCommentsResponse
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleResponse
import net.mindzone.robroo.data.forumArticle.entity.forumArticles.ForumArticlesResponse
import net.mindzone.robroo.data.forumArticle.entity.forumArticles.ResponseData
import net.mindzone.robroo.data.forumArticle.entity.forumList.ForumListResponse
import net.mindzone.robroo.data.manualDocument.entity.documentSearch.DocumentSearchResponse
import net.mindzone.robroo.data.manualDocument.entity.listDocument.ListDocumentResponse
import net.mindzone.robroo.data.manualDocument.entity.listDocumentType.ListDocumentTypeResponse
import net.mindzone.robroo.data.manualDocument.entity.listLanguage.ListLanguageResponse
import net.mindzone.robroo.data.menu.entity.ListTopMostMenuItem.ListServiceResponse
import net.mindzone.robroo.data.menu.entity.listNewUnderTopMostLevel.ListNewsTopmostResponse
import net.mindzone.robroo.data.news.entity.ListByGroupResponse
import net.mindzone.robroo.data.news.entity.newsfeedContent.NewsFeedContentResponse
import net.mindzone.robroo.data.notification.entity.NotificationResponse
import net.mindzone.robroo.data.productGetWarranty.entity.WarrantyResponse
import net.mindzone.robroo.data.productInfo.entity.InfoResponse
import net.mindzone.robroo.data.productModel.entity.FieldsReponse
import net.mindzone.robroo.data.productgroupList.entity.SubGroupsResponse
import net.mindzone.robroo.data.productgroupListproductmodel.entity.ListProductModelResponse
import net.mindzone.robroo.data.productmodelListimplement.entity.ListImplementResponse
import net.mindzone.robroo.data.subMenu.entity.SubMenuResponse
import net.mindzone.robroo.data.token.entity.TokenResponse
import net.mindzone.robroo.data.user.entity.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiClient {

    @FormUrlEncoded
    @POST("getSkaBox/users/me")
    suspend fun login(@Field("azure_id") azure_id: String): Response<LoginResponse>

    @FormUrlEncoded
    @POST("getWidgets")
    suspend fun getSubMenuApi(
        @Field("azure_id") azure_id: String,
    ): Response<SubMenuResponse>

    @FormUrlEncoded
    @POST("setPushToken")
    suspend fun setPushToken(
        @Field("token") token: String,
        @Field("azure_id") azure_id: String,
        @Field("os") os: String,
        @Field("enabled") enabled: String
    ): Response<TokenResponse>

    @FormUrlEncoded
    @POST("getNotifications")
    suspend fun getNotifications(
        @Field("type") type: String,
        @Field("azure_id") azure_id: String,
        @Field("page") page: Int
    ): Response<NotificationResponse>

    @POST("getApplicationSettings")
    suspend fun getApplicationSettings(): Response<ApplicationResponse>


    @FormUrlEncoded
    @POST("getFAQArticle")
    suspend fun getFAQArticle(
        @Field("faq_id") faq_id: String
    ): Response<FaqArticleResponse>

    @FormUrlEncoded
    @POST("getFAQList")
    suspend fun getFaqList(
        @Field("section") section: String
    ): Response<FaqListResponse>

    @POST("getForumList")
    suspend fun getForumList(): Response<ForumListResponse>

    @FormUrlEncoded
    @POST("getForumArticle")
    suspend fun getForumArticle(
        @Field("article_id") article_id: String,
        @Field("azure_id") azure_id: String
    ): Response<ForumArticleResponse>

    @FormUrlEncoded
    @POST("getForumArticles")
    suspend fun getForumArticles(
        @Field("group_id") group_id: String,
        @Field("q") searchQuery: String,
        @Field("page") page: Int,
        @Field("azure_id") azure_id: String,
        @Field("sort") sort: String,
        @Field("owner") owner: Int,
    ): Response<ForumArticlesResponse>

    @FormUrlEncoded
    @POST("getForumArticleComments")
    suspend fun getForumArticleComments(
        @Field("article_id") article_id: String,
        @Field("azure_id") azure_id: String
    ): Response<ForumArticleCommentsResponse>

    @Multipart
    @POST("createForumArticle")
    suspend fun createForumArticle(
        @Part("azure_id")  azure_id: RequestBody,
        @Part tags: List<MultipartBody.Part>,
        @Part images: List<MultipartBody.Part>,
        @Part("title")  title: RequestBody,
        @Part("text")  text: RequestBody,
        @Part("group_id")  group_id: RequestBody,
        @Part("author")  author: RequestBody
        ): Response<ResponseCode>

    @FormUrlEncoded
    @POST("createForumArticleComment")
    suspend fun createForumArticleComment(
        @Field("article_id") article_id: String,
        @Field("azure_id") azure_id: String,
        @Field("parent_id") parent_id: String,
        @Field("text") text: String,
        @Field("author") author: String
    ): Response<ResponseData>

    @FormUrlEncoded
    @POST("likeForumArticleComment")
    suspend fun likeForumArticleComment(
        @Field("article_id") article_id: String,
        @Field("azure_id") azure_id: String,
        @Field("like") like: String,
        @Field("comment_id") comment_id: String,
    ): Response<ResponseData>
    @FormUrlEncoded
    @POST("deleteForumArticle")
    suspend fun deleteForumArticle(
        @Field("azure_id") azure_id: String,
        @Field("article_id") article_id: String

    ): Response<ResponseCode>

    @FormUrlEncoded
    @POST("likeForumArticle")
    suspend fun likeForumArticle(
        @Field("article_id") article_id: String,
        @Field("azure_id") azure_id: String,
        @Field("like") like: String,
    ): Response<ResponseCode>

    @FormUrlEncoded
    @POST("getSkaBox/productmodel/{model_id}/getspecification")
    suspend fun getField(
        @Field("azure_id") azure_id: String,
        @Path(value = "model_id") model_id: String
    ): Response<FieldsReponse>

    @FormUrlEncoded
    @POST("getSkaBox/productgroup/list")
    suspend fun getSubGroups(
            @Field("azure_id") azure_id: String,
    ): Response<SubGroupsResponse>

    @FormUrlEncoded
    @POST("getSkaBox/productgroup/{group_id}/listproductmodel")
    suspend fun getListProductModel(
            @Field("azure_id") azure_id: String,
            @Path(value = "group_id") group_id: Int
    ): Response<ListProductModelResponse>



    @FormUrlEncoded
    @POST("getSkaBox/menu/listnews")
    suspend fun getListNewsTopMost(
            @Field("azure_id") azure_id: String,
    ): Response<ListNewsTopmostResponse>

    @FormUrlEncoded
    @POST("getSkaBox/news/group/{group_id}")
    suspend fun getListByGroup(
            @Field("azure_id") azure_id: String,
            @Path(value = "group_id") group_id: Int
    ): Response<ListByGroupResponse>


    @FormUrlEncoded
    @POST("getSkaBox/productmodel/{model_id}/getwarranty")
    suspend fun getWarrantyInfo(
        @Field("azure_id") azure_id: String,
        @Path(value = "model_id",encoded = true) model_id: String
    ): Response<WarrantyResponse>

    @FormUrlEncoded
    @POST("getSkaBox/productmodel/{model_id}/listimplement")
    suspend fun getListImplement(
        @Field("azure_id") azure_id: String,
        @Path(value = "model_id",encoded = true) model_id: String
        ): Response<ListImplementResponse>
    @FormUrlEncoded
    @POST("getSkaBox/productmodel/{model_id}/getinfo")
    suspend fun getInfo(
        @Field("azure_id") azure_id: String,
        @Path(value="model_id",encoded = true) model_id: String
    ): Response<InfoResponse>
    @FormUrlEncoded
    @POST("getSkaBox/news/content/{content_id}")
    suspend fun getNewsFeedContent(
            @Field("azure_id") azure_id: String,
            @Path(value = "content_id") content_id: Int
    ): Response<NewsFeedContentResponse>

    @FormUrlEncoded
    @POST("getSkaBox/manual/listlanguage")
    suspend fun getListLanguage(
            @Field("azure_id") azure_id: String
    ): Response<ListLanguageResponse>

    @FormUrlEncoded
    @POST("getSkaBox/manual/listdocumenttype")
    suspend fun getListDocumentType(
            @Field("azure_id") azure_id: String
    ): Response<ListDocumentTypeResponse>

    @FormUrlEncoded
    @POST("getSkaBox/productdocument/listdocument")
    suspend fun getListDocument(
            @Field("azure_id") azure_id: String,
            @Query(value = "start") start:Int,
            @Query(value = "size") size:Int,
            @Query(value = "search",encoded = true) model_id:String,
            @Query(value = "sort") sort:String,
            @Query(value = "doclangid") doclangid:Int?,
            @Query(value = "doctypeid") doctypeid:Int?,
    ):Response<ListDocumentResponse>

    @FormUrlEncoded
    @POST("getSkaBox/productdocument/listdocument")
    suspend fun getDocumentSearch(
        @Field("azure_id") azure_id: String,
        @Query(value = "search", encoded = true) model_id:String,
    ): Response<DocumentSearchResponse>

    @FormUrlEncoded
    @POST("getSkaBox/menu/{id}/list")
    suspend fun getListService(
            @Field("azure_id") azure_id: String,
            @Path(value = "id") id: String
    ): Response<ListServiceResponse>
    @FormUrlEncoded
    @Streaming
    @POST("getSKABoxDownload")
    suspend fun downloadPdf(
            @Field("azure_id") azure_id: String,
            @Field("downloadUrl") downloadUrl: String
    ):Response<ResponseBody>



}