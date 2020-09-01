const clone = (obj) => {
  // 深度克隆
  let o;
  if (typeof (obj) != "object" || obj === null) return obj;
  if (obj instanceof Array) {
    o = [];
    o = obj.slice(0);
  } else {
    o = {};
    for (let i in obj) {
      if (typeof (obj[i]) == "object" && obj[i] != null) {
        o[i] = this.clone(obj[i]);
      } else {
        o[i] = obj[i];
      }
    }
  }
  return o;
}

const formatDate = (date, fmt) => {
  // 日期格式化，yyyy-MM-dd EEE hh:mm:ss
  if (!fmt)
    fmt = "yyyy-MM-dd hh:mm:ss";
  var o = {
    "Y+": date.getFullYear(),
    "M+": date.getMonth() + 1,
    "d+": date.getDate(),
    "h+": date.getHours(),
    "H+": date.getHours(),
    "m+": date.getMinutes(),
    "s+": date.getSeconds(),
    "q+": Math.floor((date.getMonth() + 3) / 3),
    "S": date.getMilliseconds()
  };
  var week = {
    "0": "日",
    "1": "一",
    "2": "二",
    "3": "三",
    "4": "四",
    "5": "五",
    "6": "六"
  };
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
  }
  if (/(E+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "星期" : "周") : "") + week[date.getDay() + ""]);
  }
  for (var k in o) {
    if (new RegExp("(" + k + ")").test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    }
  }
  return fmt;
}

export default {
  clone,
  formatDate
}
