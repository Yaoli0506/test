package com.ksrcb.api.biz.depoly.util;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * JGit拉取代码
 */
public class JGitUtil {

    private String remotePath = "";// 远程库路径
    private String localPath = "";
    UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = null;

    /**
     * 构造器初始化对git操作的身份验证信息和一些初始化的信息
     * @param remotePath        请以 \\ 或 / 结尾代表路径 ex: C:\\aa\\
     * @param userName
     * @param passWord
     * @param gitLocalPath
     */
    public JGitUtil(String remotePath,String userName, String passWord, String gitLocalPath) {
        this.localPath = gitLocalPath;
        usernamePasswordCredentialsProvider = new UsernamePasswordCredentialsProvider(userName, passWord);

    }

    /**
     * 克隆远程库
     *
     * @param remoteRepositoryName
     *            远程仓库名字
     * @throws IOException
     * @throws GitAPIException
     */
    public void cloneRepository(String remoteRepositoryName) throws IOException, GitAPIException {
        // 克隆代码库命令
        CloneCommand cloneCommand = Git.cloneRepository();

        Git git = cloneCommand.setURI(remotePath) // 设置远程URI
                .setBranch(remoteRepositoryName) // 设置clone下来的分支
                .setDirectory(new File(localPath)) // 设置下载存放路径
                .setCredentialsProvider(usernamePasswordCredentialsProvider) // 设置权限验证
                .call();

        System.out.print(git.tag());
    }

    /**
     * 本地新建仓库
     *
     * @throws IOException
     */
    public void createRepository() throws IOException {
        // 本地新建仓库地址
        Repository newRepo = FileRepositoryBuilder.create(new File(localPath + ".git"));
        newRepo.create();
    }

    /**
     * 本地仓库新增文件 往本地仓库增加文件
     *
     * @param fileName
     *            文件名字
     * @throws IOException
     * @throws GitAPIException
     */
    public void addFileToRepository(String fileName) throws IOException, GitAPIException {
        File myfile = new File(localPath + fileName);
        myfile.createNewFile();
        // git仓库地址
        Git git = new Git(new FileRepository(localPath + ".git"));

        // 添加文件
        git.add().addFilepattern("myfile").call();
    }

    /**
     * 本地提交代码到远程仓库
     *
     * @param commitMessage    提交的注释信息
     * @throws IOException
     * @throws GitAPIException
     * @throws JGitInternalException
     */
    public void commitToRepository(String commitMessage) throws IOException, GitAPIException, JGitInternalException {
        // git仓库地址
        Git git = new Git(new FileRepository(localPath + ".git"));
        // 提交代码
        git.commit().setMessage(commitMessage).call();
    }

    /**
     * 拉取远程仓库内容到本地
     *
     * @param remoteRepositoryName     远程仓库名
     * @throws IOException
     * @throws GitAPIException
     */
    public void pull(String remoteRepositoryName) throws IOException, GitAPIException {

        // git仓库地址
        Git git = new Git(new FileRepository(localPath + ".git"));
        git.pull().setRemoteBranchName(remoteRepositoryName).setCredentialsProvider(usernamePasswordCredentialsProvider)
                .call();
    }

    /**
     * push本地代码到远程仓库地址
     *
     * @param remoteRepositoryName    远程仓库名
     * @throws IOException
     * @throws JGitInternalException
     * @throws GitAPIException
     */
    public void push(String remoteRepositoryName) throws IOException, JGitInternalException, GitAPIException {

        // git仓库地址
        Git git = new Git(new FileRepository(localPath + ".git"));
        git.push().setRemote(remoteRepositoryName).setCredentialsProvider(usernamePasswordCredentialsProvider).call();
    }
}
