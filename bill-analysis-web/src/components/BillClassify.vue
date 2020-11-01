<template>
  <div>
    <!--    <h1 href="#" class="category" style="font-size: 32px;margin:0 auto;width: 160px;display: block;font-family: 'Microsoft YaHei'">分类</h1><br/><br/>-->
    <!--    <draggable class="list-group" :list="list1" group="price"-->
    <!--               :animation='200'>-->
    <!--      <transition-group name='flip-list'>-->
    <!--        <div class="list-group-item" v-for="(element, index) in list1" :key="element.name"-->
    <!--             :title="element.title">-->
    <!--          {{ element.name }} {{ index }}-->
    <!--        </div>-->
    <!--      </transition-group>-->
    <!--    </draggable>-->
    <div>
      <div class="block" style="margin: 0 auto;width:500px;">
        <div style="">
          <span class="demonstration">账单月份</span>
          <el-date-picker
            v-model="billDate"
            editable=false
            value-format="yyyy-MM"
            type="month"
            placeholder="选择月"
            @change="chooseBillDate"
          >
          </el-date-picker>

          <el-button type="primary" icon="el-icon-check" style="height:40px;margin-left: 50px;" @click="save">保存分类结果
          </el-button>
        </div>
      </div>
    </div>
    <br/>

    <div style="width: 16.5667%;float: left;">
      <h1 href="#" class="category"
          style="font-size: 32px;margin:0 auto;width: 160px;display: block;font-family: 'Microsoft YaHei'"> 物&nbsp;&nbsp;&nbsp;品 </h1>
      <draggable class="list-group" :list="goods" group="goods" tag="ul"
                 :animation='200' filter=".unDraggable">
        <transition-group name='flip-list'>
          <li class="list-group-item" v-for="(element, index) in goods" :key="index">
            <a href="#">
              <h2 :title="element.to" class="goodsNameShort">{{element.to}} ：</h2>
              <div class="goodsName">
                {{element.goodName}}
              </div>
              <h2 class="price">{{element.price}}元</h2>
              <p class="orderTime">{{element.orderTime}}</p>
            </a>
          </li>
        </transition-group>
      </draggable>
    </div>
    <div
      style="float:left;margin-top: 90px;width: 1px;height: 1000px;  background-image: linear-gradient(rgba(255,255,255,0.1), rgba(204,204,204,1), rgba(255,255,255,0.1));"></div>
    <div style="width: 16.5667%;float: left;">
      <h1 href="#" class="category"
          style="font-size: 32px;margin:0 auto;width: 160px;display: block;font-family: 'Microsoft YaHei'"> 宝&nbsp;&nbsp;&nbsp;宝
        {{childTotal}}</h1>
      <draggable class="list-group" :list="child" group="goods" tag="ul"
                 :animation='200' filter=".unDraggable">
        <transition-group name='flip-list'>
          <li :class="'list-group-item '+element.dragAble" v-for="(element, index) in child" :key="index">
            <a href="#">
              <h2 :title="element.to" class="goodsNameShort">{{element.to}} ：</h2>
              <div class="goodsName">
                {{element.goodName}}
              </div>
              <h2 class="price">{{element.price}}元</h2>
              <p class="orderTime">{{element.orderTime}}</p>
            </a>
          </li>
        </transition-group>
      </draggable>
    </div>
    <div
      style="float:left;margin-top: 90px;width: 1px;height: 1000px;  background-image: linear-gradient(rgba(255,255,255,0.1), rgba(204,204,204,1), rgba(255,255,255,0.1));"></div>
    <div style="width: 16.5667%;float: left;">
      <h1 href="#" class="category"
          style="font-size: 32px;margin:0 auto;width: 160px;display: block;font-family: 'Microsoft YaHei'"> 生&nbsp;活&nbsp;费
        {{lifeTotal}}</h1>
      <draggable class="list-group" :list="life" group="goods" tag="ul"
                 :animation='200' filter=".unDraggable">
        <transition-group name='flip-list'>
          <li :class="'list-group-item '+element.dragAble" v-for="(element, index) in life" :key="index">
            <a href="#">
              <h2 :title="element.to" class="goodsNameShort">{{element.to}} ：</h2>
              <div class="goodsName">
                {{element.goodName}}
              </div>
              <h2 class="price">{{element.price}}元</h2>
              <p class="orderTime">{{element.orderTime}}</p>
            </a>
          </li>
        </transition-group>
      </draggable>
    </div>

    <div
      style="float:left;margin-top: 90px;width: 1px;height: 1000px;  background-image: linear-gradient(rgba(255,255,255,0.1), rgba(204,204,204,1), rgba(255,255,255,0.1));"></div>
    <div style="width: 16.5667%;float: left;">
      <h1 href="#" class="category"
          style="font-size: 32px;margin:0 auto;width: 160px;display: block;font-family: 'Microsoft YaHei'"> 老&nbsp;&nbsp;&nbsp;婆
        {{wifeTotal}}</h1>
      <draggable class="list-group" :list="wife" group="goods" tag="ul"
                 :animation='200' filter=".unDraggable">
        <transition-group name='flip-list'>
          <li :class="'list-group-item '+element.dragAble" v-for="(element, index) in wife" :key="index">
            <a href="#">
              <h2 :title="element.to" class="goodsNameShort">{{element.to}} ：</h2>
              <div class="goodsName">
                {{element.goodName}}
              </div>
              <h2 class="price">{{element.price}}元</h2>
              <p class="orderTime">{{element.orderTime}}</p>
            </a>
          </li>
        </transition-group>
      </draggable>
    </div>

    <div
      style="float:left;margin-top: 90px;width: 1px;height: 1000px;  background-image: linear-gradient(rgba(255,255,255,0.1), rgba(204,204,204,1), rgba(255,255,255,0.1));"></div>
    <div style="width: 16.5667%;float: left;">
      <h1 href="#" class="category"
          style="font-size: 32px;margin:0 auto;width: 160px;display: block;font-family: 'Microsoft YaHei'"> 老&nbsp;&nbsp;&nbsp;公
        {{husbandTotal}}</h1>
      <draggable class="list-group" :list="husband" group="goods" tag="ul"
                 :animation='200' filter=".unDraggable">
        <transition-group name='flip-list'>
          <li :class="'list-group-item '+element.dragAble" v-for="(element, index) in husband" :key="index">
            <a href="#">
              <h2 :title="element.to" class="goodsNameShort">{{element.to}} ：</h2>
              <div class="goodsName">
                {{element.goodName}}
              </div>
              <h2 class="price">{{element.price}}元</h2>
              <p class="orderTime">{{element.orderTime}}</p>
            </a>
          </li>
        </transition-group>
      </draggable>
    </div>

    <div
      style="float:left;margin-top: 90px;width: 1px;height: 1000px;  background-image: linear-gradient(rgba(255,255,255,0.1), rgba(204,204,204,1), rgba(255,255,255,0.1));"></div>
    <div style="width: 16.5667%;float: left;">
      <h1 href="#" class="category"
          style="font-size: 28px;margin:0 auto;width: 160px;display: block;font-family: 'Microsoft YaHei'"> 人情往来
        {{favorTotal}}</h1>
      <draggable class="list-group" :list="favor" group="goods" tag="ul"
                 :animation='200' filter=".unDraggable">
        <transition-group name='flip-list'>
          <li :class="'list-group-item '+element.dragAble" v-for="(element, index) in favor" :key="index">
            <a href="#">
              <h2 :title="element.to" class="goodsNameShort">{{element.to}} ：</h2>
              <div class="goodsName">
                {{element.goodName}}
              </div>
              <h2 class="price">{{element.price}}元</h2>
              <p class="orderTime">{{element.orderTime}}</p>
            </a>
          </li>
        </transition-group>
      </draggable>
    </div>

  </div>
</template>

<script>
  import draggable from 'vuedraggable';
  import test from '../request/test';

  export default {
    components: {
      draggable,
    },
    data() {
      return {
        // list1: [{id: 1, name: "haha", title: "title1", description: "description1"},
        //   {id: 2, name: "haha2", title: "title2", description: "description1"},
        //   {id: 3, name: "haha3", title: "title3", description: "description1"},
        //   {id: 4, name: "haha4", title: "title4", description: "description1"}],
        // list2: [{id: 1, name: "haha", title: "title5", description: "description1"},
        //   {id: 2, name: "haha2", title: "title6", description: "description1"},
        //   {id: 3, name: "haha3", title: "title7", description: "description1"},
        //   {id: 4, name: "haha4", title: "title8", description: "description1"}],


        goods: [],
        // goods: [
        //   {
        //     to: "天弘基金管理有限公司",
        //     goodName: "大码女装2020夏季新款胖mm宽松显瘦连衣裙娃娃领气质裙减龄洋气潮",
        //     price: "358.27",
        //     orderTime: "2020/8/31 13:19"
        //   },
        //   {to: "源敏贸易集团", goodName: "室外户外花箱防腐木花箱长方形花盆阳台露台种菜种树箱特大号花盆", price: "116.1", orderTime: "2020/8/31 13:19"},
        //   {to: "天弘基金管理有限公司", goodName: "欧式室内花盆架多层描金花架白色花几仿实木落地客厅阳台置物架", price: "148.26", orderTime: "2020/8/31 13:19"},
        //   {
        //     to: "芽芽乐旗舰店",
        //     goodName: "金龙鱼乳玉皇妃稻香贡米5kg 正宗东北大米 米饭 煮粥 超值10斤装 等多件",
        //     price: "221.25",
        //     orderTime: "2020/8/31 13:19"
        //   },
        //   {to: "宝宝馋了旗舰店", goodName: "Roland罗朗德黑芝麻酱350g婴幼儿食用高钙高铁营养辅食宝宝拌饭料", price: "128", orderTime: "2020/8/31 13:19"},
        //   {to: "秋田满满旗舰店", goodName: "辅食添加调味料海苔粉猪肝粉儿童淡干虾皮香菇粉搭配宝宝婴儿辅食", price: "99.9", orderTime: "2020/8/31 13:19"},
        // ],
        billDate: "",
        child: [{to: "XX", goodName: "将货物拖到这边", price: 0, orderTime: "xxxx/xx/xx xx:xx", dragAble: "unDraggable"}],
        life: [{to: "XX", goodName: "将货物拖到这边", price: 0, orderTime: "xxxx/xx/xx xx:xx", dragAble: "unDraggable"}],
        wife: [{to: "XX", goodName: "将货物拖到这边", price: 0, orderTime: "xxxx/xx/xx xx:xx", dragAble: "unDraggable"}],
        husband: [{
          to: "XX",
          goodName: "将货物拖到这边",
          price: 0,
          orderTime: "xxxx/xx/xx xx:xx",
          dragAble: "unDraggable"
        }],
        favor: [{to: "XX", goodName: "将货物拖到这边", price: 0, orderTime: "xxxx/xx/xx xx:xx", dragAble: "unDraggable"}],


        getComponentData: {
          on: {
            change: this.handleChange,
            input: this.inputChanged
          },
          attrs: {
            wrap: true
          },
          props: {
            value: this.activeNames
          }
        }

      };
    },
    methods: {
      chooseBillDate(value) {
        console.info(value);
        this.loadGoods(value);
      },
      save() {
        var billDateItem = [{
          goodName: this.billDate
        }];
        this.$axios({
          method: 'post',
          url: '/bill-analysis/uploadBillClassified',
          data: {
            child: this.child,
            life: this.life,
            wife: this.wife,
            husband: this.husband,
            favor: this.favor,
            billDate: billDateItem
          }
        }).then(res => {
          console.log(res);
          this.$message(res);
        }).catch((err) => {
          this.$message(err);
          console.log(err)
        });
      },
      handleChange() {
        console.log('changed');
      },
      inputChanged(value) {
        this.activeNames = value;
      },
      loadGoods(value) {
        let param = {
          date: value,
        };
        test.listBillOriginal(param).then((res) => {
          res.forEach((item, index, array) => {
            // to: "天弘基金管理有限公司",
            //   goodName: "大码女装2020夏季新款胖mm宽松显瘦连衣裙娃娃领气质裙减龄洋气潮",
            //   price: "358.27",
            //   orderTime: "2020/8/31 13:19"
            var itemCopy = item;
            itemCopy.to = item.payTo;
            itemCopy.goodName = item.goodName;
            itemCopy.price = item.price;
            itemCopy.orderTime = item.payTime;
            this.goods.push(itemCopy);

          });

          var total = 0;
          if (res != null) {
            total = res.length;
          }
          // console.log(res);
          this.$message.success("数据加载完成！共：" + total + "条");
        }).catch((err) => {
          this.$message.error("获取数据失败" + err);
          console.log(err)
        });
      },
    },
    mounted() {
      //this.loadGoods();
    },
    computed: {
      childTotal: function () {
        var total = 0;
        this.child.forEach((item, index, array) => {
          total += item.price;
        });
        return total;
      },
      lifeTotal: function () {
        var total = 0;
        this.life.forEach((item, index, array) => {
          total += item.price;
        });
        return total;
      },
      wifeTotal: function () {
        var total = 0;
        this.wife.forEach((item, index, array) => {
          total += item.price;
        });
        return total;
      },
      husbandTotal: function () {
        var total = 0;
        this.husband.forEach((item, index, array) => {
          total += item.price;
        });
        return total;
      },
      favorTotal: function () {
        var total = 0;
        this.favor.forEach((item, index, array) => {
          total += item.price;
        });
        return total;
      },
    },
    name: "BillClassify"
  }
</script>

<style scoped>
  .flip-list-move {
    transition: transform 0.5s;
  }

  * {
    margin: 0;
    padding: 0;
  }

  .goodsNameShort {
    width: 120px; /*要显示文字的宽度*/
    text-overflow: ellipsis; /*让截断的文字显示为点点。还有一个值是clip意截断不显示点点*/
    white-space: nowrap; /*让文字不换行*/
    overflow: hidden; /*超出要隐藏*/
  }

  .goodsName {
    font-size: 14px;
    font-weight: bold;
  }

  .price {
    font-size: 30px;
    color: red;
    padding-left: 30px;
  }

  .orderTime {
    margin-left: 70px;
    font-size: 10px;
  }


  body {
    font-family: arial, sans-serif;
    font-size: 100%;
    margin: 3em;
    background: #666;
    color: #fff;
  }

  h2, p {
    font-size: 100%;
    font-weight: normal;
  }

  ul, li {
    list-style: none;
  }

  ul {
    overflow: hidden;
    padding: 3em;
    padding-top: 1em;
  }

  ul li a {
    text-decoration: none;
    color: #000;
    background: #d8f8fa;
    display: block;
    /*height:10em;*/
    width: 10em;
    padding: 1em;
  }

  ul li {
    margin: 1em;
    float: left;
  }

  ul li h2 {
    font-size: 140%;
    font-weight: bold;
    padding-bottom: 10px;
  }

  ul li p {
    font-family: "Reenie Beanie", arial, sans-serif, 微软雅黑;
    font-size: 110%;
  }

  ul li a {
    text-decoration: none;
    color: #000;
    background: #d8f8fa;
    display: block;
    /*height: 12em;*/
    width: 10em;
    padding: 1em; /* Firefox */
    -moz-box-shadow: 5px 5px 7px rgba(33, 33, 33, 1); /* Safari+Chrome */
    -webkit-box-shadow: 5px 5px 7px rgba(33, 33, 33, .7); /* Opera */
    box-shadow: 5px 5px 7px rgba(33, 33, 33, .7);
  }

  ul li a {
    -webkit-transform: rotate(-6deg);
    -o-transform: rotate(-6deg);
    -moz-transform: rotate(-6deg);
  }

  ul li:nth-child(even) a {
    -o-transform: rotate(4deg);
    -webkit-transform: rotate(4deg);
    -moz-transform: rotate(4deg);
    position: relative;
    top: 5px;
  }

  ul li:nth-child(3n) a {
    -o-transform: rotate(-3deg);
    -webkit-transform: rotate(-3deg);
    -moz-transform: rotate(-3deg);
    position: relative;
    top: -5px;
  }

  ul li:nth-child(5n) a {
    -o-transform: rotate(5deg);
    -webkit-transform: rotate(5deg);
    -moz-transform: rotate(5deg);
    position: relative;
    top: -10px;
  }

  ul li a:hover, ul li a:focus {
    -moz-box-shadow: 10px 10px 7px rgba(0, 0, 0, .7);
    -webkit-box-shadow: 10px 10px 7px rgba(0, 0, 0, .7);
    box-shadow: 10px 10px 7px rgba(0, 0, 0, .7);
    -webkit-transform: scale(1.25);
    -moz-transform: scale(1.25);
    -o-transform: scale(1.25);
    position: relative;
    z-index: 5;
  }

  ul li:nth-child(even) a {
    -o-transform: rotate(4deg);
    -webkit-transform: rotate(4deg);
    -moz-transform: rotate(4deg);
    position: relative;
    top: 5px;
    background: #faecfa;
  }

  ul li:nth-child(3n) a {
    -o-transform: rotate(-3deg);
    -webkit-transform: rotate(-3deg);
    -moz-transform: rotate(-3deg);
    position: relative;
    top: -5px;
    background: #e6f6e6;
  }


  .category {
    background-color: #3bb3e0;
    width: 150px;
    display: inline-block;
    font-family: Arial, Helvetica, sans-serif;
    font-size: 12px;
    text-align: center;
    text-decoration: none;
    color: #fff;
    position: relative;
    margin-top: 40px;
    padding-bottom: 2px;
    padding-top: 2px;
    background-image: linear-gradient(bottom, rgb(44, 160, 202) 0%, rgb(62, 184, 229) 100%);
    background-image: -o-linear-gradient(bottom, rgb(44, 160, 202) 0%, rgb(62, 184, 229) 100%);
    background-image: -moz-linear-gradient(bottom, rgb(44, 160, 202) 0%, rgb(62, 184, 229) 100%);
    background-image: -webkit-linear-gradient(bottom, rgb(44, 160, 202) 0%, rgb(62, 184, 229) 100%);
    background-image: -ms-linear-gradient(bottom, rgb(44, 160, 202) 0%, rgb(62, 184, 229) 100%);
    background-image: -webkit-gradient(
      linear,
      left bottom,
      left top,
      color-stop(0, rgb(44, 160, 202)),
      color-stop(1, rgb(62, 184, 229))
    );
    -webkit-border-bottom-right-radius: 5px;
    -webkit-border-bottom-left-radius: 5px;
    -moz-border-radius-bottomright: 5px;
    -moz-border-radius-bottomleft: 5px;
    border-bottom-right-radius: 5px;
    border-bottom-left-radius: 5px;
    -webkit-box-shadow: inset 0px 1px 0px #2ab7ec, 0px 5px 0px 0px #156785, 0px 10px 5px #999;
    -moz-box-shadow: inset 0px 1px 0px #2ab7ec, 0px 5px 0px 0px #156785, 0px 10px 5px #999;
    -o-box-shadow: inset 0px 1px 0px #2ab7ec, 0px 5px 0px 0px #156785, 0px 10px 5px #999;
    box-shadow: inset 0px 1px 0px #2ab7ec, 0px 5px 0px 0px #156785, 0px 10px 5px #999;


  }

  .category:active {
    top: 0px;
    background-image: linear-gradient(bottom, rgb(62, 184, 229) 0%, rgb(44, 160, 202) 100%);
    background-image: -o-linear-gradient(bottom, rgb(62, 184, 229) 0%, rgb(44, 160, 202) 100%);
    background-image: -moz-linear-gradient(bottom, rgb(62, 184, 229) 0%, rgb(44, 160, 202) 100%);
    background-image: -webkit-linear-gradient(bottom, rgb(62, 184, 229) 0%, rgb(44, 160, 202) 100%);
    background-image: -ms-linear-gradient(bottom, rgb(62, 184, 229) 0%, rgb(44, 160, 202) 100%);
    background-image: -webkit-gradient(
      linear,
      left bottom,
      left top,
      color-stop(0, rgb(62, 184, 229)),
      color-stop(1, rgb(44, 160, 202))
    );
    -webkit-box-shadow: inset 0px 4px 1px #0b698b, 0px 2px 0px 0px #156785, 0px 5px 3px #999;
    -moz-box-shadow: inset 0px 4px 1px #0b698b, 0px 2px 0px 1px #156785, 0px 5px 3px #999;
    -o-box-shadow: inset 0px 4px 1px #0b698b, 0px 2px 0px 1px #156785, 0px 5px 3px #999;
    box-shadow: inset 0px 4px 1px #0b698b, 0px 2px 0px 0px #156785, 0px 5px 3px #999;
  }

  .category::before {
    background-color: #fff;
    /*background-image:url(Pic/heart.gif);*/
    background-repeat: no-repeat;
    background-position: center center;
    content: "";
    width: 150px;
    height: 40px;
    position: absolute;
    top: -30px;
    left: 0px;
    margin-top: -11px;
    z-index: -1;
    -webkit-border-top-left-radius: 5px;
    -webkit-border-top-right-radius: 5px;
    -moz-border-radius-topleft: 5px;
    -moz-border-radius-topright: 5px;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
  }

  .category:active::before {
    top: -30px;
    -webkit-box-shadow: 0px 3px 0px #ccc;
    -moz-box-shadow: 0px 3px 0px #ccc;
    -o-box-shadow: 0px 3px 0px #ccc;
    box-shadow: 0px 3px 0px #ccc;
  }


</style>
