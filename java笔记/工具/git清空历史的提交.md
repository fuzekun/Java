# git清空历史的提交



```shell
git checkout --orphan nb						# 创建一个新的提交
git add -A										#下面两步重新提交
git commit -m "first commit"					
git branch -D master							# 删除原来的分支
git branch -m master							# 把当前分支命名为原来的分支
git push origin master							# 重新上传即可
```

