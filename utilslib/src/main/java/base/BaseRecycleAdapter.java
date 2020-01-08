package base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author linzi
 * @date 2019/6/11 0011
 * @context 通用适配器
 */
public class BaseRecycleAdapter extends RecyclerView.Adapter {
    public interface AdapterCallBack<T extends RecyclerView.ViewHolder>{
         T bindVh(ViewGroup parent);
         void bindData(T vh, int position);
         void onItemClickListener(int position);
    }
    private int size=0;
    private AdapterCallBack callBack;

    public void setSize(int size) {
        this.size = size;
        this.notifyDataSetChanged();
    }

    public void setCallBack(AdapterCallBack callBack) {
        this.callBack = callBack;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(callBack!=null) {
            return callBack.bindVh(parent);
        }else{
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(callBack!=null){
            callBack.bindData(holder,position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onItemClickListener(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }
}
