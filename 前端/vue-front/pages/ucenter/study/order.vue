<template>
  <article class="col-7 fl">
    <div class="u-r-cont">
      <section>
        <div>
          <section class="c-infor-tabTitle c-tab-title">

            <a href="javascript: void(0)" title="我的订单" class="current">
              订单列表
            </a>
          </section>
        </div>
        <div class="mt40">
          <section v-if="orderList.length === 0" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam" >您还没有订单哦！</span>
          </section>

          <!-- 表格 -->
          <el-table
            v-if="orderList.length>0"
            :data="orderList"
            border
            fit
            highlight-current-row>

            <el-table-column label="课程信息" align="center" >
              <template slot-scope="scope">
                <div class="info" >
                  <div class="pic">
                    <img :src="scope.row.courseCover" alt="scope.row.courseTitle" width="100px">
                  </div>
                  <div class="title">
                    <a :href="'/course/'+scope.row.courseId">{{ scope.row.courseTitle }}</a>
                  </div>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="讲师名称" width="100" align="center">
              <template slot-scope="scope">
                {{ scope.row.teacherName }}
              </template>
            </el-table-column>
            <el-table-column label="价格" width="100" align="center" >
              <template slot-scope="scope">
                {{ scope.row.totalFee }}
              </template>
            </el-table-column>
            <el-table-column label="创建时间" width="180" align="center">
              <template slot-scope="scope">
                {{ scope.row.gmtCreate }}
              </template>
            </el-table-column>
            <el-table-column prop="tradeState" label="订单状态" width="100" align="center" >
              <template slot-scope="scope">
                <el-tag :type="scope.row.tradeState === 0 ? 'warning' : 'success'">{{ scope.row.tradeState === 0 ? '未支付' : '已支付' }}</el-tag>
              </template>
            </el-table-column>

            <el-table-column label="操作" width="150" align="center">
              <template slot-scope="scope" >
                <router-link v-if="scope.row.tradeState === 0" :to="'/orders/'+scope.row.orderNo">
                  <el-button type="text" size="mini" icon="el-icon-edit">去支付</el-button>
                </router-link>
                <router-link v-if="scope.row.tradeState === 1" :to="'/course/'+scope.row.courseId">
                  <el-button type="text" size="mini" icon="el-icon-edit">去学习</el-button>
                </router-link>
                <!-- v-if="scope.row.tradeState === 0" -->
                <i 
                  class="el-icon-delete"
                  style="cursor:pointer"
                  title="删除订单"
                  @click="removeById(scope.row.id)"/>
              </template>

            </el-table-column>
          </el-table>
          <!-- 分页 -->
          <el-pagination v-if="orderList.length !== 0"
            :current-page="page"
            :page-size="limit"
            :total="total"
            style="padding: 30px 0; text-align: center"
            layout="total, prev, pager, next, jumper"
            @current-change="fetchOrderList"
          />
        </div>
      </section>
    </div>
  </article>
</template>

<script>
  //引入调用order.js文件
  import orderApi from '@/api/orders'

  export default {
    data(){
      return{
        page: 1,
        limit: 3,
        orderList:[],
        total: 0,
      }
    },

    created() {
      this.fetchOrderList()
    },

    methods:{
      fetchOrderList(page = 1){
        this.page = page;
        orderApi.getList(this.page, this.limit).then(response => {
          this.orderList = response.data.data.items
          this.total = response.data.data.total;
        })
      },

      removeById(id) {
            this.$confirm('确认要删除当前订单吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              return orderApi.removeById(id)
            }).then((response) => {
              this.fetchOrderList()
              this.$message({
                type: 'success',
                message: '删除成功✌'
              })
            }).catch(error => {
              if (error === 'cancel') {
                this.$message({
                  message: '取消删除'
                })
              }
            })
          }
    },
  }
</script>
