<template>
  <div class="home-con">home page</div>
</template>

<script>
import utils from '../lib/utils';
import vuescroll from 'vuescroll';
import Mock from '../../mock/mock';
import test from '../request/test';

export default {
  name: 'home',
  data() {
    return {
      scrolloperation: { // 滚动条配置项
        bar: {
          size: '6px',
          opacity: 0.8,
          keepShow: true,
          background: '#bde6ff',
          onlyShowBarOnScroll: false
        },
        rail: {
          size: '6px'
        }
      },
    }
  },
  mounted() {
    // rpc请求
    test.testRpc({
      type: 'rpc',
      bean: 'bshipbf.helloRpcServiceImpl',
      method: 'testMultiParams',
      body: ["sunzj", 0]
    }).then(res => {
      if (res.data.code == '200') {
        console.log(res.data)
      } else {
        console.log(res.data)
      }
    });

    // 请求Mock数据
    test.testMock().then((res) => {
      console.log(res)
    }).catch((err) => {
      console.log(err)
    });
  }
}
</script>

<style scoped>
.home-con {
  width: 100%;
  height: 100%;
  position: relative;
}
</style>
