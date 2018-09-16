local result
result = redis.call('get',KEYS[1])
if result then
    if(tonumber(ARGV[1]) > tonumber(result)) then
        redis.call('set',KEYS[1],tostring(tonumber(ARGV[1])+tonumber(ARGV[2])))
        return ARGV[1]
    else
        redis.call('set',KEYS[1],tostring(tonumber(result)+tonumber(ARGV[2])))
        return result
    end
else
    redis.call('set',KEYS[1],tostring(tonumber(ARGV[1])+tonumber(ARGV[2])))
    redis.call('expire',KEYS[1],24*60*60)
    return ARGV[1]
end

