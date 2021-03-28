<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程详情 开始 -->
    <section class="container">
      <section class="path-wrap txtOf hLh30">
        <a href="#" title class="c-999 fsize14">首页</a>
        \
        <a href="#" title class="c-999 fsize14">{{courseWebVo.subjectLevelOne}}</a>
        \
        <span class="c-333 fsize14">{{courseWebVo.subjectLevelTwo}}</span>
      </section>
      <div>
        <article class="c-v-pic-wrap" style="height: 357px;">
          <section class="p-h-video-box" id="videoPlay" style="background:#263b3c;">
            <img height="358px" width="647px" :src="courseWebVo.cover" :alt="courseWebVo.title" class="dis c-v-pic">
          </section>
        </article>
        <aside class="c-attr-wrap" style="background:#2c6b3f;">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{courseWebVo.title}}</span>
            </h2>
            <section class="c-attr-jg">
              <span class="c-fff">价格：</span>
              <b class="c-yellow" style="font-size:24px;">￥{{courseWebVo.price}}</b>
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">主讲： {{courseWebVo.teacherName}}&nbsp;&nbsp;&nbsp;</span>
            </section>
            <section class="c-attr-mt of">
                <span v-if="isCollect" class="ml10 vam sc-end">
                    <em class="icon18 scIcon"/>
                    <a
                      style="cursor:pointer"
                      class="c-fff vam"
                      title="取消收藏"
                      @click="removeCollect(courseWebVo.id)">已收藏</a>
                </span>
                <span v-else class="ml10 vam">
                    <em class="icon18 scIcon"/>
                    <span
                      style="cursor:pointer"
                      class="c-fff vam"
                      title="收藏"
                      @click="addCollect(courseWebVo.id)" >收藏</span>
                </span>
            </section>
            <section  v-if="isbuy || Number(courseWebVo.price) === 0" class="c-attr-mt">
              <a href="#" title="立即观看" class="comm-btn c-btn-3">立即观看</a>
            </section>
            <section  v-else class="c-attr-mt">
              <a @click="createOrders()" href="#" title="立即购买" class="comm-btn c-btn-3">立即购买</a>
            </section>
          </section>
        </aside>
        <aside class="thr-attr-box" style="background:#263b3c;">
          <ol class="thr-attr-ol">
            <!-- <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">购买数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseWebVo.buyCount}}</h6>
              </aside>
            </li> -->
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">课时数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseWebVo.lessonNum}}</h6>
              </aside>
            </li>
            <li style="margin-top:80px;">
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">浏览数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseWebVo.viewCount/2}}</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"></div>
      </div>
      <!-- /课程封面介绍 -->
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="课程详情">课程详情</a>
                </section>
              </div>
              <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
                <el-tab-pane label="课程详情" name="first">
                  <article class="ml10 mr10 pt20">
                      <div>
                        <h6 class="c-i-content c-infor-title">
                          <span>课程介绍</span>
                        </h6>
                        <div class="course-txt-body-wrap">
                          <section class="course-txt-body">
                            <p v-html="courseWebVo.description">{{courseWebVo.description}}</p>
                          </section>
                        </div>
                      </div>
                      <!-- /课程介绍 -->
                      <div class="mt50">
                        <h6 class="c-g-content c-infor-title">
                          <span>课程大纲</span>
                        </h6>
                        <section class="mt20">
                          <div class="lh-menu-wrap">
                            <menu id="lh-menu" class="lh-menu mt10 mr10">
                              <ul>
                                <!-- 文件目录 -->
                                <li class="lh-menu-stair" v-for="chapter in chapterVideoList" :key="chapter.id">
                                  <a href="javascript: void(0)" :title="chapter.title" class="current-1">
                                    <em class="lh-menu-i-1 icon18 mr10"></em>{{chapter.title}}
                                  </a>

                                  <ol class="lh-menu-ol" style="display: block;">
                                    <li class="lh-menu-second ml30" v-for="video in chapter.children" :key="video.id">
                                      <!-- 在小节中判断是否购买课程，免费课程可以试听，付费课程需要购买 -->
                                      <a 
                                        v-if="isbuy || Number(courseWebVo.price) === 0"
                                        :href="'/player/'+video.videoSourceId" target="_blank" 
                                        :title="video.title">
                                        <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{video.title}}
                                      </a>
                                      
                                      <a 
                                          v-else-if="video.isFree === true"
                                          :href="'/player/'+video.videoSourceId" target="_blank" >
                                        <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{video.title}}
                                        <span class="fr">
                                            <i class="free-icon vam mr10">免费试听</i>
                                        </span>
                                      </a>
                                      <a
                                        v-else
                                        :title="video.title">
                                        <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{ video.title }}
                                      </a>

                                    </li>
                                    
                                  </ol>

                                </li>
                              </ul>
                            </menu>
                          </div>
                        </section>
                      </div>
                      <!-- /课程大纲 -->
                    </article>
            </el-tab-pane>

    <!------------------------- 课程评论 --------------------------------------------->
                
       <el-tab-pane label="课程评论" name="second">
              <div class="mt50 commentHtml"><div>
                <h6 class="c-c-content c-infor-title" id="i-art-comment">
                  <span class="commentTitle">课程评论</span>
                </h6>
                <section class="lh-bj-list pr mt20 replyhtml">
                  <ul>
                    <li class="unBr">
                      <aside class="noter-pic">
                        <img width="50" height="50" class="picImg" src="~/assets/img/avatar-boy.gif">
                        </aside>
                      <div class="of">
                        <section class="n-reply-wrap">
                          <fieldset>
                            <textarea name="" v-model="comment.content" placeholder="输入您要评论的文字" id="commentContent"></textarea>
                          </fieldset>
                          <p class="of mt5 tar pl10 pr10">
                            <span class="fl "><tt class="c-red commentContentmeg" style="display: none;"></tt></span>
                            <input type="button" @click="addComment()" value="回复" class="lh-reply-btn">
                          </p>
                        </section>
                      </div>
                    </li>
                  </ul>
                </section>
                <section class="">
                    <section class="question-list lh-bj-list pr">
                      <ul class="pr10">
                        <li v-for="(comment,index) in data.items" v-bind:key="index">
                            <aside class="noter-pic">
                              <img width="50" height="50" class="picImg" :src="comment.avatar">
                              </aside>
                            <div class="of">
                              <span class="fl"> 
                              <font class="fsize12 c-blue"> 
                                {{comment.nickname}}</font>
                              <font class="fsize12 c-999 ml5">评论：</font></span>
                            </div>
                            <div class="noter-txt mt5">
                              <p>{{comment.content}}</p>
                            </div>
                            <div class="of mt5">
                              <span class="fr"><font class="fsize12 c-999 ml5">{{comment.gmtCreate}}</font></span>
                            </div>
                          </li>
                        
                        </ul>
                    </section>
                  </section>
                  
                  <!-- 公共分页 开始 -->
                  <div class="paging">
                      <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
                      <a
                      :class="{undisable: !data.hasPrevious}"
                      href="#"
                      title="首页"
                      @click.prevent="gotoPage(1)">首</a>
                      <a
                      :class="{undisable: !data.hasPrevious}"
                      href="#"
                      title="前一页"
                      @click.prevent="gotoPage(data.current-1)">&lt;</a>
                      <a
                      v-for="page in data.pages"
                      :key="page"
                      :class="{current: data.current == page, undisable: data.current == page}"
                      :title="'第'+page+'页'"
                      href="#"
                      @click.prevent="gotoPage(page)">{{ page }}</a>
                      <a
                      :class="{undisable: !data.hasNext}"
                      href="#"
                      title="后一页"
                      @click.prevent="gotoPage(data.current+1)">&gt;</a>
                      <a
                      :class="{undisable: !data.hasNext}"
                      href="#"
                      title="末页"
                      @click.prevent="gotoPage(data.pages)">末</a>
                      <div class="clear"/>
                  </div>
                  <!-- 公共分页 结束 -->
                </div>
              </div>
                  
      </el-tab-pane>            
    </el-tabs>              
    <!-- <el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane>
    <el-tab-pane label="定时任务补偿" name="fourth">定时任务补偿</el-tab-pane> -->  

    <!--------------------- Tabs 标签页结束 -------------------------------------->
            </div>
          </section>
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">主讲讲师</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto;">
                  <li>
                    <div class="u-face">
                      <a href="#">
                        <img :src="courseWebVo.avatar" width="50" height="50" alt>
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" href="#">{{courseWebVo.teacherName}}</a>
                    </section>
                    <section class="hLh20 txtOf">
                      <span class="c-999">{{courseWebVo.intro}}</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div class="clear"></div>
      </div>
    </section>
    <!-- /课程详情 结束 -->
    
    
  </div>
</template>

<script>
import courseApi from '@/api/course'
import ordersApi from '@/api/orders'
import comment from '@/api/commonedu'
import collectApi from '@/api/collect'
import cookie from 'js-cookie'
import { Message, MessageBox } from 'element-ui' 

export default {
   asyncData({ params, error }) {
     return {courseId: params.id}
   },
   data() {
     return {
       activeName: 'first',
        data:{},
        page:1,
        limit:4,
        total:10,
        comment:{
          content:'',
          courseId:'',
          teacherId:'',
        },
        courseWebVo: {},
        chapterVideoList: [],
        isbuy: false,
        isCollect: false // 是否已收藏
     }
   },
   created() {//在页面渲染之前执行
      this.initCourseInfo()
      this.initComment()
   },
   methods:{
      handleClick(tab, event) {
        console.log(tab, event);
      },
     //查询课程详情信息
     initCourseInfo() {
        if (cookie.get('guli_token')) {
          //把获取cookie值放到header里面
          //config.headers['token'] = cookie.get('guli_token');
        } else {
          this.$router.push({path:'/login/'})
        }
        courseApi.getCourseInfo(this.courseId)
          .then(response => {
            
            this.courseWebVo=response.data.data.courseWebVo,
            this.chapterVideoList=response.data.data.chapterVideoList,
            this.isbuy=response.data.data.isBuy
        })
        //判断是否收藏
        collectApi.isCollect(this.courseId).then(response => {
            this.isCollect = response.data.data.isCollect
        })
     },
     //生成订单
     createOrders() {
       ordersApi.createOrders(this.courseId)
        .then(response => {
          //获取返回订单号
          //生成订单之后，跳转订单显示页面
          this.$router.push({path:'/orders/'+response.data.data.orderId})
        })
     },
    initComment(){
    comment.getPageList(this.page, this.limit, this.courseId).then(response => {
        this.data = response.data.data
      })
    },

    addComment(){
        this.comment.courseId = this.courseId
        this.comment.teacherId = this.courseWebVo.teacherId
        comment.addComment(this.comment).then(response => {
            if(response.data.success){
                this.comment.content = ''
                this.initComment()
            }
        })
    },

    gotoPage(page){
      comment.getPageList(page, this.limit,this.courseId).then(response => {
          this.data = response.data.data
      })
    },
    //收藏
    addCollect(courseId) {
      collectApi.addCollect(this.courseId).then(response => {
        this.isCollect = true
        this.$message.success(response.message)
      })
    },

    //取消收藏
    removeCollect(courseId) {
      collectApi.removeById(this.courseId).then(response => {
        this.isCollect = false
        this.$message.success(response.message)
      })
    }

  }
};
</script>
