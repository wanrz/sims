<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>ECharts</title>
</head>
<body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="height:400px"></div>
</body>
<!-- ECharts单文件引入 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/echart/dist/echarts.js"></script>
<script type="text/javascript">
    // 路径配置
    require.config({
        paths: {
            echarts: '${pageContext.request.contextPath}/static/echart/dist'
        }
    });
 	// 使用
    require(
        [
            'echarts',
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('main')); 
            
            var option = {
          		title: {
          	        text: "服装销售变化",
          	        subtext: "",
          	        x: "left",
          	        textStyle: {
          	            color: "#366",
          	            fontSize: 18
          	        }
          	    },tooltip: {
                    show: true
                },
                legend: {
                	x: "center",
                    data:['销量','销售额']
                },
                xAxis : [
                    {
                        type : 'category',
                        data : ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        "name":"销量",
                        "type":"bar",
                        "data":[5, 20, 60, 10, 10, 20]
                    },{
                        "name":"销售额",
                        "type":"bar",
                        "data":[5, 20, 40, 10, 10, 20]
                    }
                ]
            };
    
            // 为echarts对象加载数据 
            myChart.setOption(option); 
        }
    );
</script>
</html>