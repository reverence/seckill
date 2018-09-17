var seckill= {
    startSeckill: function (goodsId, node,killUrl) {
        //执行秒杀
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        //绑定一次点击事件
        $('#killBtn').one('click', function () {
            $(this).addClass('disabled');
            //发送秒杀请求执行秒杀
            $.post(killUrl, {goodsId:goodsId}, function (result) {
                if (result && result['success']) {
                    var killResult = result['data'];
                    var resultInfo = killResult['resultInfo'];
                    //显示秒杀结果
                    node.html('<span class="label label-success">' + resultInfo + '</span>');
                }
            });
        });
        node.show();
    },
    countDown: function (goodsId, currentTime, startTime, endTime) {
        var seckillaArea = $('#seckillArea');
        if (currentTime > endTime) {
            //秒杀结束
            seckillaArea.html('秒杀结束!');
        } else if (currentTime < startTime) {
            var killTime = new Date(startTime + 1000);
            seckillaArea.countdown(killTime, function (event) {
                //时间格式
                var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒 ');
                seckillaArea.html(format);
            }).on('finish.countdown', function () {
                //时间完成后回调事件
                location.reload();
            });
        }
    },
    // 详情页秒杀逻辑
    detail : {
        // 详情页初始化
        init: function(goodsId,startTime,endTime,killed,currentTime,killUrl) {
            // 已经秒杀过
            var seckillaArea = $('#seckillArea');
            if(killed){
                var resultInfo = '您已成功秒杀过该商品';
                seckillaArea.hide().html('<span class="label label-success">' + resultInfo + '</span>');
                seckillaArea.show();
                return;
            }
            if(killUrl != ""){
                seckill.startSeckill(goodsId,seckillaArea,killUrl);
            }else{
                // 计时
                seckill.countDown(goodsId,currentTime,startTime,endTime);
            }
        }
    }
}