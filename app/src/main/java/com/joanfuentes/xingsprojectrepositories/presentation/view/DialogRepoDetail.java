package com.joanfuentes.xingsprojectrepositories.presentation.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.joanfuentes.xingsprojectrepositories.R;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import javax.inject.Inject;

public class DialogRepoDetail {
    @Inject
    public DialogRepoDetail() {}

    public void show(Context context, Repo repo) {
        Resources resources = context.getResources();
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setTitle(repo.getName());
        builder.setMessage(repo.getDescription());
        builder.setPositiveButton(resources.getString(R.string.go_to_repo),
                getOnCLickListener(context, repo.getHtmlUrl()));
        builder.setNegativeButton(resources.getString(R.string.go_to_owners_repo),
                getOnCLickListener(context, repo.getOwnerHtmlUrl()));
        builder.show();
    }

    @NonNull
    private DialogInterface.OnClickListener getOnCLickListener(final Context context, final String htmlUrl) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                navigateTo(context, htmlUrl);
            }
        };
    }

    private void navigateTo(Context context, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }
}
