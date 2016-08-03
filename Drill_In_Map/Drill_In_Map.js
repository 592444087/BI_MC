define(["./echarts-all"],
function() {
		var flag = true;
		var dataSet = [];
		var b=800;
		var c=320;
	//var width_m=$element.width()*0.8+'px';
	//var height_m=$element.height()*0.8+'px';
		var option = {
						    tooltip : {
						        trigger: 'item',
						        formatter: '{b}:{c}'
						    },
						    dataRange: {
						        min: 0,
						        max: 1000,
						        //color:['orange','yellow'],
								//color:['#CC4C02','yellow'],
								color:['#7E0000','yellow'],
						        text:['高','低'],           // 文本，默认为数值文本
						        calculable : true
						    },
						    series : [
						        {
						            name: '随机数据',
						            type: 'map',
						            mapType: 'china',
						            selectedMode : 'single',
						            roam : true,
						            scaleLimit : {max:2,min:0.5},
									mapLocation : {width : b,height:c},
						            itemStyle:{
						                normal:{label:{show:true}},
						                emphasis:{label:{show:true}}
						            },
						            data:dataSet
						        }
						    ]
						};
		var cityMap = {
			"石家庄市": "130100",
			"唐山市": "130200",
			"秦皇岛市": "130300",
			"邯郸市": "130400",
			"邢台市": "130500",
			"保定市": "130600",
			"张家口市": "130700",
			"承德市": "130800",
			"沧州市": "130900",
			"廊坊市": "131000",
			"衡水市": "131100",
			"太原市": "140100",
			"大同市": "140200",
			"阳泉市": "140300",
			"长治市": "140400",
			"晋城市": "140500",
			"朔州市": "140600",
			"晋中市": "140700",
			"运城市": "140800",
			"忻州市": "140900",
			"临汾市": "141000",
			"吕梁市": "141100",
			"呼和浩特市": "150100",
			"包头市": "150200",
			"乌海市": "150300",
			"赤峰市": "150400",
			"通辽市": "150500",
			"鄂尔多斯市": "150600",
			"呼伦贝尔市": "150700",
			"巴彦淖尔市": "150800",
			"乌兰察布市": "150900",
			"兴安盟": "152200",
			"锡林郭勒盟": "152500",
			"阿拉善盟": "152900",
			"沈阳市": "210100",
			"大连市": "210200",
			"鞍山市": "210300",
			"抚顺市": "210400",
			"本溪市": "210500",
			"丹东市": "210600",
			"锦州市": "210700",
			"营口市": "210800",
			"阜新市": "210900",
			"辽阳市": "211000",
			"盘锦市": "211100",
			"铁岭市": "211200",
			"朝阳市": "211300",
			"葫芦岛市": "211400",
			"长春市": "220100",
			"吉林市": "220200",
			"四平市": "220300",
			"辽源市": "220400",
			"通化市": "220500",
			"白山市": "220600",
			"松原市": "220700",
			"白城市": "220800",
			"延边朝鲜族自治州": "222400",
			"哈尔滨市": "230100",
			"齐齐哈尔市": "230200",
			"鸡西市": "230300",
			"鹤岗市": "230400",
			"双鸭山市": "230500",
			"大庆市": "230600",
			"伊春市": "230700",
			"佳木斯市": "230800",
			"七台河市": "230900",
			"牡丹江市": "231000",
			"黑河市": "231100",
			"绥化市": "231200",
			"大兴安岭地区": "232700",
			"南京市": "320100",
			"无锡市": "320200",
			"徐州市": "320300",
			"常州市": "320400",
			"苏州市": "320500",
			"南通市": "320600",
			"连云港市": "320700",
			"淮安市": "320800",
			"盐城市": "320900",
			"扬州市": "321000",
			"镇江市": "321100",
			"泰州市": "321200",
			"宿迁市": "321300",
			"杭州市": "330100",
			"宁波市": "330200",
			"温州市": "330300",
			"嘉兴市": "330400",
			"湖州市": "330500",
			"绍兴市": "330600",
			"金华市": "330700",
			"衢州市": "330800",
			"舟山市": "330900",
			"台州市": "331000",
			"丽水市": "331100",
			"合肥市": "340100",
			"芜湖市": "340200",
			"蚌埠市": "340300",
			"淮南市": "340400",
			"马鞍山市": "340500",
			"淮北市": "340600",
			"铜陵市": "340700",
			"安庆市": "340800",
			"黄山市": "341000",
			"滁州市": "341100",
			"阜阳市": "341200",
			"宿州市": "341300",
			"巢湖市": "341400",
			"六安市": "341500",
			"亳州市": "341600",
			"池州市": "341700",
			"宣城市": "341800",
			"福州市": "350100",
			"厦门市": "350200",
			"莆田市": "350300",
			"三明市": "350400",
			"泉州市": "350500",
			"漳州市": "350600",
			"南平市": "350700",
			"龙岩市": "350800",
			"宁德市": "350900",
			"南昌市": "360100",
			"景德镇市": "360200",
			"萍乡市": "360300",
			"九江市": "360400",
			"新余市": "360500",
			"鹰潭市": "360600",
			"赣州市": "360700",
			"吉安市": "360800",
			"宜春市": "360900",
			"抚州市": "361000",
			"上饶市": "361100",
			"济南市": "370100",
			"青岛市": "370200",
			"淄博市": "370300",
			"枣庄市": "370400",
			"东营市": "370500",
			"烟台市": "370600",
			"潍坊市": "370700",
			"济宁市": "370800",
			"泰安市": "370900",
			"威海市": "371000",
			"日照市": "371100",
			"莱芜市": "371200",
			"临沂市": "371300",
			"德州市": "371400",
			"聊城市": "371500",
			"滨州市": "371600",
			"菏泽市": "371700",
			"郑州市": "410100",
			"开封市": "410200",
			"洛阳市": "410300",
			"平顶山市": "410400",
			"安阳市": "410500",
			"鹤壁市": "410600",
			"新乡市": "410700",
			"焦作市": "410800",
			"濮阳市": "410900",
			"许昌市": "411000",
			"漯河市": "411100",
			"三门峡市": "411200",
			"南阳市": "411300",
			"商丘市": "411400",
			"信阳市": "411500",
			"周口市": "411600",
			"驻马店市": "411700",
			"武汉市": "420100",
			"黄石市": "420200",
			"十堰市": "420300",
			"宜昌市": "420500",
			"襄樊市": "420600",
			"鄂州市": "420700",
			"荆门市": "420800",
			"孝感市": "420900",
			"荆州市": "421000",
			"黄冈市": "421100",
			"咸宁市": "421200",
			"随州市": "421300",
			"恩施土家族苗族自治州": "422800",
			"长沙市": "430100",
			"株洲市": "430200",
			"湘潭市": "430300",
			"衡阳市": "430400",
			"邵阳市": "430500",
			"岳阳市": "430600",
			"常德市": "430700",
			"张家界市": "430800",
			"益阳市": "430900",
			"郴州市": "431000",
			"永州市": "431100",
			"怀化市": "431200",
			"娄底市": "431300",
			"湘西土家族苗族自治州": "433100",
			"广州市": "440100",
			"韶关市": "440200",
			"深圳市": "440300",
			"珠海市": "440400",
			"汕头市": "440500",
			"佛山市": "440600",
			"江门市": "440700",
			"湛江市": "440800",
			"茂名市": "440900",
			"肇庆市": "441200",
			"惠州市": "441300",
			"梅州市": "441400",
			"汕尾市": "441500",
			"河源市": "441600",
			"阳江市": "441700",
			"清远市": "441800",
			"东莞市": "441900",
			"中山市": "442000",
			"潮州市": "445100",
			"揭阳市": "445200",
			"云浮市": "445300",
			"南宁市": "450100",
			"柳州市": "450200",
			"桂林市": "450300",
			"梧州市": "450400",
			"北海市": "450500",
			"防城港市": "450600",
			"钦州市": "450700",
			"贵港市": "450800",
			"玉林市": "450900",
			"百色市": "451000",
			"贺州市": "451100",
			"河池市": "451200",
			"来宾市": "451300",
			"崇左市": "451400",
			"海口市": "460100",
			"三亚市": "460200",
			"成都市": "510100",
			"自贡市": "510300",
			"攀枝花市": "510400",
			"泸州市": "510500",
			"德阳市": "510600",
			"绵阳市": "510700",
			"广元市": "510800",
			"遂宁市": "510900",
			"内江市": "511000",
			"乐山市": "511100",
			"南充市": "511300",
			"眉山市": "511400",
			"宜宾市": "511500",
			"广安市": "511600",
			"达州市": "511700",
			"雅安市": "511800",
			"巴中市": "511900",
			"资阳市": "512000",
			"阿坝藏族羌族自治州": "513200",
			"甘孜藏族自治州": "513300",
			"凉山彝族自治州": "513400",
			"贵阳市": "520100",
			"六盘水市": "520200",
			"遵义市": "520300",
			"安顺市": "520400",
			"铜仁地区": "522200",
			"黔西南布依族苗族自治州": "522300",
			"毕节地区": "522400",
			"黔东南苗族侗族自治州": "522600",
			"黔南布依族苗族自治州": "522700",
			"昆明市": "530100",
			"曲靖市": "530300",
			"玉溪市": "530400",
			"保山市": "530500",
			"昭通市": "530600",
			"丽江市": "530700",
			"普洱市": "530800",
			"临沧市": "530900",
			"楚雄彝族自治州": "532300",
			"红河哈尼族彝族自治州": "532500",
			"文山壮族苗族自治州": "532600",
			"西双版纳傣族自治州": "532800",
			"大理白族自治州": "532900",
			"德宏傣族景颇族自治州": "533100",
			"怒江傈僳族自治州": "533300",
			"迪庆藏族自治州": "533400",
			"拉萨市": "540100",
			"昌都地区": "542100",
			"山南地区": "542200",
			"日喀则地区": "542300",
			"那曲地区": "542400",
			"阿里地区": "542500",
			"林芝地区": "542600",
			"西安市": "610100",
			"铜川市": "610200",
			"宝鸡市": "610300",
			"咸阳市": "610400",
			"渭南市": "610500",
			"延安市": "610600",
			"汉中市": "610700",
			"榆林市": "610800",
			"安康市": "610900",
			"商洛市": "611000",
			"兰州市": "620100",
			"嘉峪关市": "620200",
			"金昌市": "620300",
			"白银市": "620400",
			"天水市": "620500",
			"武威市": "620600",
			"张掖市": "620700",
			"平凉市": "620800",
			"酒泉市": "620900",
			"庆阳市": "621000",
			"定西市": "621100",
			"陇南市": "621200",
			"临夏回族自治州": "622900",
			"甘南藏族自治州": "623000",
			"西宁市": "630100",
			"海东地区": "632100",
			"海北藏族自治州": "632200",
			"黄南藏族自治州": "632300",
			"海南藏族自治州": "632500",
			"果洛藏族自治州": "632600",
			"玉树藏族自治州": "632700",
			"海西蒙古族藏族自治州": "632800",
			"银川市": "640100",
			"石嘴山市": "640200",
			"吴忠市": "640300",
			"固原市": "640400",
			"中卫市": "640500",
			"乌鲁木齐市": "650100",
			"克拉玛依市": "650200",
			"吐鲁番地区": "652100",
			"哈密地区": "652200",
			"昌吉回族自治州": "652300",
			"博尔塔拉蒙古自治州": "652700",
			"巴音郭楞蒙古自治州": "652800",
			"阿克苏地区": "652900",
			"克孜勒苏柯尔克孜自治州": "653000",
			"喀什地区": "653100",
			"和田地区": "653200",
			"伊犁哈萨克自治州": "654000",
			"塔城地区": "654200",
			"阿勒泰地区": "654300"
		};						 
    return {
            definition: {
				type: "items",
				component: "accordion",
				items: {
					dimensions: {
						uses: "dimensions",
						min:1,
						max:1
					},
					measures: {
						uses: "measures",
						min:1,
						max:1
					},
					sorting: {
						uses: "sorting"
					},
					appearance: {
						uses: "settings"
					}
				}
			},
			snapshot : {
				canTakeSnapshot : true
			},			
			initialProperties: {
				qHyperCubeDef: {
					qDimensions: [],
					qMeasures: [],
					qInitialDataFetch: [
						{
							qWidth: 10,
							qHeight: 100
						}
					]
				}
			},
 	
        paint: function($element,layout) {
            //add your rendering code here          
				var self = this;
				function dataDriven(index) {
			        var dim_count = layout.qHyperCube.qDimensionInfo.length;
			        var qMatrix = layout.qHyperCube.qDataPages[0].qMatrix;
			        for(var q=0;q<qMatrix.length;q++){
			        	
			        	var d = qMatrix[q];
			        	if( d[0]['qText']===index){ //['slice'](0,-1)
				            d.dim = function(i) {
				                return d[i-1];
				            };
				            d.measure = function(i) {
				                return d[i+dim_count-1];
				            };
				            for (var i = 0; i<dim_count; i++) {
				                d[0].qSelf = self;
				                d[0].qIndex = i;
				                d[0].qSelf.backendApi.selectValues(d[0].qIndex,[d[0].qElemNumber],true);
				            };	
			          	}
			        }		
			}            
            var hc = layout.qHyperCube;
            			
						$element.empty();
						if($element.height()!=0 && $element.width()!=0){
							var divStr = '<div id="main" style="height:'+$element.height()+'px;'+'width:'+$element.width()+'px;"></div>';
						}else{
							var divStr = '<div id="main" style="height:600px;width:800px;"></div>';
						}
				//var width_m=$element.width()*0.8+'px';
				//var height_m=$element.height()*0.8+'px';
				//option.series[0].mapLocation.width = $element.width()*0.9+'px';
				//option.series[0].mapLocation.height = $element.height()*0.9+'px';
						var table = $(divStr);
						var promise = $element.append( table ).promise();
						dataSet.length = 0;
						for(var r = 0; r < hc.qDataPages[0].qMatrix.length; r++){
							var dataObj = {name:hc.qDataPages[0].qMatrix[r][0].qText,value:hc.qDataPages[0].qMatrix[r][1].qText};
							dataSet.push(dataObj);
							}
						option.series[0].data = dataSet;	
						var ecConfig = echarts.config;
						var zrEvent = zrender.tool.event;
						var curIndx = 0;
						var mapType = [
						    'china',
						    // 23个省
						    '广东', '青海', '四川', '海南', '陕西', 
						    '甘肃', '云南', '湖南', '湖北', '黑龙江',
						    '贵州', '山东', '江西', '河南', '河北',
						    '山西', '安徽', '福建', '浙江', '江苏', 
						    '吉林', '辽宁', '台湾',
						    // 5个自治区
						    '新疆', '广西', '宁夏', '内蒙古', '西藏', 
						    // 4个直辖市
						    '北京', '天津', '上海', '重庆',
						    // 2个特别行政区
						    '香港', '澳门'
						];
						var mapGeoData = echarts.util.mapData.params;
						function myfunction(){
								var myChart = echarts.init(document.getElementById("main"));
								for (var city in cityMap) {
								    mapType.push(city);
								    // 自定义扩展图表类型
								    mapGeoData.params[city] = {
								        getGeoJson: (function (c) {
								            var geoJsonName = cityMap[c];
									            return function (callback) {
																$.getJSON('http://localhost:4848/Extensions/Map_Drill_Down/china-main-city/' + geoJsonName + '.json', callback);
									            }
								        })(city)
								    }
								}
								if($("li[data-csid='DIM_AREA.AREA_PROVINCE']").length==0){
									option.series[0].mapType = 'china';
								}else if($("li[data-csid='DIM_AREA.AREA_PROVINCE']").length>0 && $("li[data-csid='DIM_AREA.AREA_CITY']").length==0){
									var type = $("li[data-csid='DIM_AREA.AREA_PROVINCE']").find("span.values.ng-binding").text();
									var mapChange = false;
									for(var i=0;i<mapType.length;i++){
										if(type == mapType[i]){
											option.series[0].mapType = type;
											mapChange = true;
											break;
										}
									}
									if(!mapChange){
										option.series[0].mapType = 'china';
									}
								}else if($("li[data-csid='DIM_AREA.AREA_CITY']").length>0 && $("li[data-csid='DIM_AREA.AREA_NAME']").length==0){
									var type = $("li[data-csid='DIM_AREA.AREA_CITY']").find("span.values.ng-binding").text();									
									var mapChange = false;
									for(var i=0;i<mapType.length;i++){
										if(type == mapType[i]){
											option.series[0].mapType = type;
											mapChange = true;
											break;
										}
									}
									if(!mapChange){
										type = $("li[data-csid='DIM_AREA.AREA_PROVINCE']").find("span.values.ng-binding").text();
										for(var i=0;i<mapType.length;i++){
											if(type == mapType[i]){
												option.series[0].mapType = type;
												mapChange = true;
												break;
											}
										}
										if(!mapChange){
											option.series[0].mapType = 'china';
										}										
									}								
								}else if($("li[data-csid='DIM_AREA.AREA_NAME']").length>0){
									var type = $("li[data-csid='DIM_AREA.AREA_CITY']").find("span.values.ng-binding").text();
									option.series[0].mapType = type;
								}
								//option.dataRange.max = layout.qHyperCube.qMeasureInfo[0].qMax+2;
								option.dataRange.max = 1500;
								myChart.setOption(option,true);
								myChart.on(ecConfig.EVENT.MAP_SELECTED, function (param){
									dataDriven(param.target);							
								    option.series[0].data = dataSet;
								    /*
								    var mt = param.target;
								    var len = mapType.length;
								    var f= false;
								    for(var i=0;i<len;i++){
								        if(mt == mapType[i]){
								              f =true;
								              mt=mapType[i];
								        }
								     }
									  if(!f){
									    mt=type;
									  }
									  option.series[0].mapType = mt;
									  */
								    myChart.setOption(option,true);
								});
						}
						
						promise.done(myfunction);              						
        }
    };
});