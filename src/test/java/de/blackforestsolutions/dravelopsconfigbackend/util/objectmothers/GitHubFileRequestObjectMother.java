package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.github.GitHubFileRequest;

public class GitHubFileRequestObjectMother{

    public static GitHubFileRequest getCorrectGitHubFileRequest() {
        GitHubFileRequest gitHubFileRequest = new GitHubFileRequest();
        gitHubFileRequest.setMessage("file test 2");
        gitHubFileRequest.setContent("LS0tCnNoYTogIjQ2NmMxMTQ5ODYzYmE4NzM5ODhiMzhhMGFlZDQ3N2JmY2U5\n" +
                "NjhlN2MiCmdyYXBocWw6CiAgcGxheWdyb3VuZDoKICAgIHBhZ2UtdGl0bGU6\n" +
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
                "ICAgICAgICBsYXllcnM6ICJ2ZW51ZSxhZGRyZXNzLHN0cmVldCxsb2NhbGl0\n" +
                "eSIKICAgICAgTkVBUkVTVF9BRERSRVNTRVM6CiAgICAgICAgbmFtZTogIk5l\n" +
                "YXJlc3QgYWRkcmVzc2VzIHF1ZXJ5IgogICAgICAgIHF1ZXJ5OiAiY2xhc3Nw\n" +
                "YXRoOnBsYXlncm91bmQvcmVxdWVzdHMvZ2V0LW5lYXJlc3QtYWRkcmVzc2Vz\n" +
                "LXF1ZXJ5LW1heC1wYXJhbWV0ZXJzLmdyYXBocWwiCiAgICAgICAgdmFyaWFi\n" +
                "bGVzOgogICAgICAgICAgbG9uZ2l0dWRlOiA4LjIwOTE5OAogICAgICAgICAg\n" +
                "bGF0aXR1ZGU6IDQ4LjA0ODM4MQogICAgICAgICAgcmFkaXVzSW5LaWxvbWV0\n" +
                "ZXJzOiAxLjAKICAgICAgICAgIGxhbmd1YWdlOiAiZGUiCiAgICAgICAgbWF4\n" +
                "UmVzdWx0czogMTAKICAgICAgICBsYXllcnM6ICJ2ZW51ZSxhZGRyZXNzLHN0\n" +
                "cmVldCxsb2NhbGl0eSIKICAgICAgTkVBUkVTVF9TVEFUSU9OUzoKICAgICAg\n" +
                "ICBuYW1lOiAiTmVhcmVzdCBzdGF0aW9ucyBxdWVyeSIKICAgICAgICBxdWVy\n" +
                "eTogImNsYXNzcGF0aDpwbGF5Z3JvdW5kL3JlcXVlc3RzL2dldC1uZWFyZXN0\n" +
                "LXN0YXRpb25zLXF1ZXJ5LW1heC1wYXJhbWV0ZXJzLmdyYXBocWwiCiAgICAg\n" +
                "ICAgdmFyaWFibGVzOgogICAgICAgICAgbG9uZ2l0dWRlOiA4LjIwOTE5OAog\n" +
                "ICAgICAgICAgbGF0aXR1ZGU6IDQ4LjA0ODM4MQogICAgICAgICAgcmFkaXVz\n" +
                "SW5LaWxvbWV0ZXJzOiAxLjAKICAgICAgICAgIGxhbmd1YWdlOiAiZGUiCiAg\n" +
                "ICAgICAgbWF4UmVzdWx0czogMTAKICAgICAgQUxMX1NUQVRJT05TOgogICAg\n" +
                "ICAgIG5hbWU6ICJHZXQgYWxsIHN0YXRpb25zIHF1ZXJ5IgogICAgICAgIHF1\n" +
                "ZXJ5OiAiY2xhc3NwYXRoOmdyYXBocWwvZ2V0LWFsbC1zdGF0aW9ucy1xdWVy\n" +
                "eS5ncmFwaHFsIgogICAgICBPUEVSQVRJTkdfQVJFQToKICAgICAgICBuYW1l\n" +
                "OiAiR2V0IG9wZXJhdGluZyBhcmVhIHF1ZXJ5IgogICAgICAgIHF1ZXJ5OiAi\n" +
                "Y2xhc3NwYXRoOmdyYXBocWwvZ2V0LXBvbHlnb24tcXVlcnkuZ3JhcGhxbCIK\n" +
                "ICAgICAgICBidWZmZXJJbk1ldHJlczogMAo=\n");
        gitHubFileRequest.setSha("466c1149863ba873988b38a0aed477bfce968e7c");
        return gitHubFileRequest;
    }
}