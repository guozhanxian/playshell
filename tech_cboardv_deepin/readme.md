1. 修改路由配置文件router/index.js
2. 修改网络api配置文件，util/https/api.js文件
3. 新编写Hello.vue页面，引用组件显示图标。
4. 修改components/widgets/ChartContent.vue文件，加入第381～384行，加入对面积折线图等的支持。
5. 在每一个组件的vue文件中，可以修改Dashboard默认的显示高度。例如，components/dashboard/widgets/ChartContent.vue文件中，第111行代码，就是用来指定默认高度的。
6. 在后台配置看板的时候，可以添加列的时候给出制定高度，以及制定宽度，用1到12表示份数（可以指定宽度的份数）。
7. 堆叠线、面积折线默认CBoard-V是不支持的，通过修改源码加入支持。修改components/widgets/ChartContent.vue文件，加入第381～384行代码。代码片段如下：
```java
let seriesItem = {
    type: values[j].series_type,
     name: name,
     barMaxWidth: 40,
     data: []
};
//加入代码，处理堆叠线和面积折线支持。   添加人：果占先<guozhanxian@gmail.com>
if (values[j].series_type.indexOf('arealine') !== -1 || values[j].series_type.indexOf('stackline') !== -1) {
  seriesItem.type = 'line';
  seriesItem.areaStyle = {};
}
//根据 option 设置 bar 的样式
if (styleOption.bar) {
 if (styleOption.bar.width !== '') seriesItem.barWidth = styleOption.bar.width;
 if (styleOption.bar.maxWidth !== '') seriesItem.barMaxWidth = styleOption.bar.maxWidth;
 if (styleOption.bar.minHeight !== '') seriesItem.barMinHeight = styleOption.bar.minHeight;
}
```
8. 堆叠柱状图，默认是不支持的。修改components/widgets/ChartContent.vue文件，将原来的第372行的程序拿到下面，放到对stackbar判断之后进行。代码片段如下：
```java
//------tmp end-----
let seriesItem = {
   type: values[j].series_type,
   name: name,
   barMaxWidth: 40,
   data: []
};
if (values[j].series_type.indexOf('stackbar') !== -1) {
   seriesItem.stack = '总量';
}
//------ tmp：有些 series_type 为 percentbar，将其转为 bar ------            
// if (values[j].series_type.indexOf('bar') !== -1) values[j].series_type = 'bar';
if (values[j].series_type.indexOf('bar') !== -1) {
 seriesItem.type = 'bar';
}

//加入代码，处理堆叠线和面积折线支持。   添加人：果占先<guozhanxian@gmail.com>
if (values[j].series_type.indexOf('arealine') !== -1 || values[j].series_type.indexOf('stackline') !== -1) {
 seriesItem.type = 'line';
 seriesItem.areaStyle = {};
}
```
9. 加入对百分比柱状图的支持。修改components/widgets/ChartContent.vue第382行代码，加入对百分比柱状图percentbar的判断支持。代码片段如下：
```java
//------tmp end-----
let seriesItem = {
  type: values[j].series_type,
  name: name,
  barMaxWidth: 40,
  data: []
};
//加入堆叠柱状图，判断类型，加入新属性stack。添加人：果占先<guozhanxian@gmail.com>
//加入百分比柱状图的支持，判断是否有percentbar类型，如果有加入支持。果占先<guozhanxian@gmail.com>
if (values[j].series_type.indexOf('stackbar') !== -1 || values[j].series_type.indexOf('percentbar') !== -1) {
  seriesItem.stack = '总量';
  seriesItem.barMaxWidth = 40 * groups.length;
}
//------ tmp：有些 series_type 为 percentbar，将其转为 bar ------            
// if (values[j].series_type.indexOf('bar') !== -1) values[j].series_type = 'bar';
if (values[j].series_type.indexOf('bar') !== -1) {
  seriesItem.type = 'bar';
}
```
10. 加入对散点图的支持。默认是可以使用散点图的，但是上面的点不会安装数值显示不同大小的点，加入这个支持。同时，修改默认的高度（第89行，修改成500px）。修改文件components/widgets/ScatterContent.vue文件，屏蔽第273行代码，加入第344行代码，返回值即为散点图的点的大小。代码片段如下：
```java
// 加入散点图点的大小。返回值即为点的大小。果占先<guozhanxian@gmail.com>
symbolSize: function (data) {
  let res = data[1];
  while (res > 50) {
    res = res / 50;
  }
  return res;
}
```
11. 默认情况下雷达图执行有异常，通过研究，修改原始代码。修改默认的高度（第102行，修改成500px）。修改文件components/widgets/RadarContent.vue文件，修改第303行代码，代码片段如下：
```java
//修改代码，解决错误问题。需要对数据中的所有数据进行处理。遍历data中的每一个key，然后访问其中的数据，如果有数据加入集合。
//修改人：果占先<guozhanxian@gmail.com>
for (let ooo in data) {
  let itemValue = data[ooo][value.name][value.aggType][key.join('-')];
  if (itemValue)
    item.value.push(itemValue);
}
```
