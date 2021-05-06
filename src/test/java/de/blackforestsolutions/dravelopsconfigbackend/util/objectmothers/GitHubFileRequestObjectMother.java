package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.github.GitHubFileRequest;

public class GitHubFileRequestObjectMother{

    public static GitHubFileRequest getCorrectGitHubFileRequest() {
        GitHubFileRequest gitHubFileRequest = new GitHubFileRequest();
        gitHubFileRequest.setMessage("file test 2");
        gitHubFileRequest.setContent("LS0tCnNoYTogIjkyNTJmOWFkN2YwMWI1ZDgwZTJjMzQ2ZmQ5MTQwZmRlZGY0\n" +
                "N2YzNzgiCmdyYXBocWw6CiAgcGxheWdyb3VuZDoKICAgIHBhZ2UtdGl0bGU6\n" +
                "ICJTdWVkYmFkZW4tQXBpIgogICAgc2V0dGluZ3M6CiAgICAgIGVkaXRvcjoK\n" +
                "ICAgICAgICB0aGVtZTogImxpZ2h0IgogICAgdGFiczoKICAgICAgSk9VUk5F\n" +
                "WV9RVUVSWToKICAgICAgICBuYW1lOiAiSm91cm5leSBxdWVyeSIKICAgICAg\n" +
                "ICBxdWVyeTogImNsYXNzcGF0aDpwbGF5Z3JvdW5kL3JlcXVlc3RzL2dldC1q\n" +
                "b3VybmV5cy1xdWVyeS1tYXgtcGFyYW1ldGVycy5ncmFwaHFsIgogICAgICAg\n" +
                "IHZhcmlhYmxlczoKICAgICAgICAgIGRlcGFydHVyZUxhdGl0dWRlOiA0OC4w\n" +
                "NDgzODEKICAgICAgICAgIGRlcGFydHVyZUxvbmdpdHVkZTogOC4yMDkxOTgK\n" +
                "ICAgICAgICAgIGFycml2YWxMYXRpdHVkZTogNDguMDg3NTE3CiAgICAgICAg\n" +
                "ICBhcnJpdmFsTG9uZ2l0dWRlOiA3Ljg5MTU5NQogICAgICAgICAgZGF0ZVRp\n" +
                "bWU6ICIyMDIxLTAzLTE3VDEzOjAwOjAwKzAyOjAwIgogICAgICAgICAgaXNB\n" +
                "cnJpdmFsRGF0ZVRpbWU6IGZhbHNlCiAgICAgICAgICBsYW5ndWFnZTogImRl\n" +
                "IgogICAgICAgIG1heFJlc3VsdHM6IDUKICAgICAgICBkZXBhcnR1cmVQbGFj\n" +
                "ZWhvbGRlcjogIlN0YXJ0IgogICAgICAgIGFycml2YWxQbGFjZWhvbGRlcjog\n" +
                "IlppZWwiCiAgICAgICAgbWF4UGFzdERheXNJbkNhbGVuZGFyOiAwCiAgICAg\n" +
                "IEpPVVJORVlfU1VCU0NSSVBUSU9OOgogICAgICAgIG5hbWU6ICJKb3VybmV5\n" +
                "IHN1YnNjcmlwdGlvbiIKICAgICAgICBxdWVyeTogImNsYXNzcGF0aDpwbGF5\n" +
                "Z3JvdW5kL3JlcXVlc3RzL2dldC1qb3VybmV5cy1zdWJzY3JpcHRpb24tbWF4\n" +
                "LXBhcmFtZXRlcnMuZ3JhcGhxbCIKICAgICAgICB2YXJpYWJsZXM6CiAgICAg\n" +
                "ICAgICBkZXBhcnR1cmVMYXRpdHVkZTogNDguMDQ4MzgxCiAgICAgICAgICBk\n" +
                "ZXBhcnR1cmVMb25naXR1ZGU6IDguMjA5MTk4CiAgICAgICAgICBhcnJpdmFs\n" +
                "TGF0aXR1ZGU6IDQ4LjA4NzUxNwogICAgICAgICAgZGF0ZVRpbWU6ICIyMDIx\n" +
                "LTAzLTE3VDEzOjAwOjAwKzAyOjAwIgogICAgICAgICAgaXNBcnJpdmFsRGF0\n" +
                "ZVRpbWU6IGZhbHNlCiAgICAgICAgICBsYW5ndWFnZTogImRlIgogICAgICAg\n" +
                "IG1heFJlc3VsdHM6IDUKICAgICAgICBkZXBhcnR1cmVQbGFjZWhvbGRlcjog\n" +
                "IlN0YXJ0IgogICAgICAgIGFycml2YWxQbGFjZWhvbGRlcjogIlppZWwiCiAg\n" +
                "ICAgICAgbWF4UGFzdERheXNJbkNhbGVuZGFyOiAwCiAgICAgIEFERFJFU1Nf\n" +
                "QVVUT0NPTVBMRVRJT046CiAgICAgICAgbmFtZTogIkF1dG9jb21wbGV0ZSBh\n" +
                "ZGRyZXNzZXMgcXVlcnkiCiAgICAgICAgcXVlcnk6ICJjbGFzc3BhdGg6cGxh\n" +
                "eWdyb3VuZC9yZXF1ZXN0cy9nZXQtYXV0b2NvbXBsZXRlLWFkZHJlc3Nlcy1x\n" +
                "dWVyeS1tYXgtcGFyYW1ldGVycy5ncmFwaHFsIgogICAgICAgIHZhcmlhYmxl\n" +
                "czoKICAgICAgICAgIHRleHQ6ICJGcmVpYnVyZyBpbSBCcmVpc2dhdSIKICAg\n" +
                "ICAgICAgIGxhbmd1YWdlOiAiZGUiCiAgICAgICAgbWF4UmVzdWx0czogMTAK\n" +
                "ICAgICAgICBsYXllcnM6CiAgICAgICAgLSAidmVudWUiCiAgICAgICAgLSAi\n" +
                "YWRkcmVzcyIKICAgICAgICAtICJzdHJlZXQiCiAgICAgICAgLSAibG9jYWxp\n" +
                "dHkiCiAgICAgIE5FQVJFU1RfQUREUkVTU0VTOgogICAgICAgIG5hbWU6ICJO\n" +
                "ZWFyZXN0IGFkZHJlc3NlcyBxdWVyeSIKICAgICAgICBxdWVyeTogImNsYXNz\n" +
                "cGF0aDpwbGF5Z3JvdW5kL3JlcXVlc3RzL2dldC1uZWFyZXN0LWFkZHJlc3Nl\n" +
                "cy1xdWVyeS1tYXgtcGFyYW1ldGVycy5ncmFwaHFsIgogICAgICAgIHZhcmlh\n" +
                "YmxlczoKICAgICAgICAgIGxvbmdpdHVkZTogOC4yMDkxOTgKICAgICAgICAg\n" +
                "IGxhdGl0dWRlOiA0OC4wNDgzODEKICAgICAgICAgIHJhZGl1c0luS2lsb21l\n" +
                "dGVyczogMS4wCiAgICAgICAgICBsYW5ndWFnZTogImRlIgogICAgICAgIG1h\n" +
                "eFJlc3VsdHM6IDEwCiAgICAgICAgbGF5ZXJzOgogICAgICAgIC0gInZlbnVl\n" +
                "IgogICAgICAgIC0gImFkZHJlc3MiCiAgICAgICAgLSAic3RyZWV0IgogICAg\n" +
                "ICAgIC0gImxvY2FsaXR5IgogICAgICBORUFSRVNUX1NUQVRJT05TOgogICAg\n" +
                "ICAgIG5hbWU6ICJOZWFyZXN0IHN0YXRpb25zIHF1ZXJ5IgogICAgICAgIHF1\n" +
                "ZXJ5OiAiY2xhc3NwYXRoOnBsYXlncm91bmQvcmVxdWVzdHMvZ2V0LW5lYXJl\n" +
                "c3Qtc3RhdGlvbnMtcXVlcnktbWF4LXBhcmFtZXRlcnMuZ3JhcGhxbCIKICAg\n" +
                "ICAgICB2YXJpYWJsZXM6CiAgICAgICAgICBsb25naXR1ZGU6IDguMjA5MTk4\n" +
                "CiAgICAgICAgICBsYXRpdHVkZTogNDguMDQ4MzgxCiAgICAgICAgICByYWRp\n" +
                "dXNJbktpbG9tZXRlcnM6IDEuMAogICAgICAgICAgbGFuZ3VhZ2U6ICJkZSIK\n" +
                "ICAgICAgICBtYXhSZXN1bHRzOiAxMAogICAgICBBTExfU1RBVElPTlM6CiAg\n" +
                "ICAgICAgbmFtZTogIkdldCBhbGwgc3RhdGlvbnMgcXVlcnkiCiAgICAgICAg\n" +
                "cXVlcnk6ICJjbGFzc3BhdGg6Z3JhcGhxbC9nZXQtYWxsLXN0YXRpb25zLXF1\n" +
                "ZXJ5LmdyYXBocWwiCiAgICAgIE9QRVJBVElOR19BUkVBOgogICAgICAgIG5h\n" +
                "bWU6ICJHZXQgb3BlcmF0aW5nIGFyZWEgcXVlcnkiCiAgICAgICAgcXVlcnk6\n" +
                "ICJjbGFzc3BhdGg6Z3JhcGhxbC9nZXQtcG9seWdvbi1xdWVyeS5ncmFwaHFs\n" +
                "IgogICAgICAgIGJ1ZmZlckluTWV0cmVzOiAwCg==\n");
        gitHubFileRequest.setSha("9252f9ad7f01b5d80e2c346fd9140fdedf47f378");
        return gitHubFileRequest;
    }
}