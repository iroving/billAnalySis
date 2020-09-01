<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
html,
body {
  margin: 0;
  padding: 0;
}
#app {
  width: 100%;
  height: 100%;
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
a {
  text-decoration: none;
}
li {
  list-style: none;
}
/* 滚动条 */
::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}
/* 滚动条滑块 */
::-webkit-scrollbar-thumb {
  border-radius: 10px;
  background: #bde6ff;
  box-shadow: inset 0 0 6px #bde6ff;
}
::-webkit-scrollbar-thumb:window-inactive {
  background: #bde6ff;
}

/* 滚动条 */
::-moz-scrollbar {
  width: 6px;
  height: 6px;
}
/* 滚动条滑块 */
::-moz-scrollbar-thumb {
  border-radius: 10px;
  background: #bde6ff;
  box-shadow: inset 0 0 6px #bde6ff;
}
::-moz-scrollbar-thumb:window-inactive {
  background: #bde6ff;
}

/* 滚动条 */
::-ms-scrollbar {
  width: 6px;
  height: 6px;
}
/* 滚动条滑块 */
::-ms-scrollbar-thumb {
  border-radius: 10px;
  background: #bde6ff;
  box-shadow: inset 0 0 6px #bde6ff;
}
::-ms-scrollbar-thumb:window-inactive {
  background: #bde6ff;
}

/* 滚动条 */
::-o-scrollbar {
  width: 6px;
  height: 6px;
}
/* 滚动条滑块 */
::-o-scrollbar-thumb {
  border-radius: 10px;
  background: #bde6ff;
  box-shadow: inset 0 0 6px #bde6ff;
}
::-o-scrollbar-thumb:window-inactive {
  background: #bde6ff;
}
</style>
