package com.lucky.cat.client;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lucky.cat.updown.download.DownloadListener;
import com.lucky.cat.updown.download.DownloadManage;
import com.lucky.cat.updown.download.DownloadRequest;

import java.util.List;

/**
 * Created by jisx on 2016/10/18.
 */

public class DownloadAdapter extends BaseAdapter {

    Context context;

    private List<DownloadRequest> list;

    DownloadAdapter(Context context, List<DownloadRequest> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_downloading, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DownloadRequest request = list.get(position);
        holder.fileName.setText(request.getModel().getFileName());


        holder.completeSize.setText(setCompleteSize(request.getModel().getCompleteSize()));
        holder.progress.setProgress((int) (request.getModel().getCompleteSize() / (request.getModel().getFileSize() * 1.0) * 100));
        holder.checkbox.setVisibility(View.INVISIBLE);
        holder.setRequest(request);
        DownloadManage.INSTANCE.addListener(request, new DownloadListen(holder, request));

        setViewHolder(holder, request);

        return convertView;
    }

    private void setViewHolder(ViewHolder holder, final DownloadRequest request) {
        switch (request.downloadType) {
            case PREPARE:
                holder.statusIcon.setImageResource(0);
                holder.operation.setImageResource(R.drawable.icon_download);
                holder.operation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DownloadManage.INSTANCE.startTask(request.getModel());
                    }
                });
                break;
            case START:
                holder.statusIcon.setImageResource(R.drawable.icon_wait);
                holder.operation.setImageResource(R.drawable.icon_stop);
                holder.operation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DownloadManage.INSTANCE.startTask(request.getModel());
                    }
                });
                break;
            case LOADING:
                holder.statusIcon.setImageResource(R.drawable.icon_downloading);
                holder.operation.setImageResource(R.drawable.icon_stop);
                holder.operation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DownloadManage.INSTANCE.pauseTask(request.getModel());
                    }
                });
                break;
            case STOP:
                holder.statusIcon.setImageResource(R.drawable.icon_status_stop);
                holder.operation.setImageResource(R.drawable.icon_start);
                holder.operation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DownloadManage.INSTANCE.startTask(request.getModel());
                    }
                });
                break;
        }
    }


    private String setCompleteSize(long bytesRead) {
        if (bytesRead > 1024 * 1024) {
            return TextUtils.formatDouble(bytesRead / (1024.0 * 1024.0)) + "MB";
        } else if (bytesRead > 1024) {
            return (int) (bytesRead / 1024) + "KB";
        } else {
            return bytesRead + "B";
        }
    }

    class DownloadListen implements DownloadListener {

        DownloadRequest downloadRequest;

        ViewHolder holder;

        DownloadListen(ViewHolder holder, DownloadRequest request) {
            this.downloadRequest = request;
            this.holder = holder;
        }

        @Override
        public void onPrepare(DownloadRequest request) {
            handler.sendEmptyMessage(0);
        }

        @Override
        public void onStart(final DownloadRequest request) {
            if (!holder.getRequest().equals(request)) {
                return;
            }

            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!holder.getRequest().equals(request)) {
                        return;
                    }
                    setViewHolder(holder, holder.getRequest());
                }
            });
        }

        @Override
        public void onLoading(final DownloadRequest request) {

            if (!holder.getRequest().equals(request)) {
                return;
            }

            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!holder.getRequest().equals(request)) {
                        return;
                    }
                    holder.completeSize.setText(setCompleteSize(request.getModel().getCompleteSize()));
                    holder.progress.setProgress((int) (request.getModel().getCompleteSize() / (request.getModel().getFileSize() * 1.0) * 100));
                    setViewHolder(holder, holder.getRequest());
                }
            });
        }

        @Override
        public void onStop(final DownloadRequest request) {
            handler.sendEmptyMessage(0);
        }

        @Override
        public void onCancel(final DownloadRequest request) {
            if (!holder.getRequest().equals(request)) {
                return;
            }

            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!holder.getRequest().equals(request)) {
                        return;
                    }
                    setViewHolder(holder, holder.getRequest());
                }
            });
        }

        @Override
        public void onComplete(final DownloadRequest request) {
           handler.sendEmptyMessage(0);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    notifyDataSetChanged();
                    break;

            }
        }
    };

    class ViewHolder {

        ImageView statusIcon;

        TextView fileName;

        TextView completeSize;

        TextView status;

        ProgressBar progress;

        ImageView operation;

        CheckBox checkbox;

        DownloadRequest request;

        ViewHolder(View view) {
            statusIcon = (ImageView) view.findViewById(R.id.status_icon);
            fileName = (TextView) view.findViewById(R.id.file_name);
            completeSize = (TextView) view.findViewById(R.id.complete_size);
            status = (TextView) view.findViewById(R.id.status);
            progress = (ProgressBar) view.findViewById(R.id.progress);
            operation = (ImageView) view.findViewById(R.id.operation);
            checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        }


        public DownloadRequest getRequest() {
            return request;
        }

        public void setRequest(DownloadRequest request) {
            this.request = request;
        }
    }

}
