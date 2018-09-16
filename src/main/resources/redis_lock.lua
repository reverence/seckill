local result
result = redis.call('get',KEYS[1])
if result then
    return false

else
    redis.call('set',KEYS[1],ARGV[1])
    redis.call('expire',KEYS[1],tonumber(ARGV[2]))
    return true
end

