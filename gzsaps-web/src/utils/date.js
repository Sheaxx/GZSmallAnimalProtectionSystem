export default function getNowTime() {
  var myDate = new Date();
  //获取当前年
  var year = myDate.getFullYear();
  //获取当前月
  var month = myDate.getMonth() + 1;
  //获取当前日
  var date = myDate.getDate();
  var h = myDate.getHours();       //获取当前小时数(0-23)
  var m = myDate.getMinutes();     //获取当前分钟数(0-59)
  var s = myDate.getSeconds();
  return year + '-' + month + "-" + date + " " + h + ':' + m + ":" + s;
}