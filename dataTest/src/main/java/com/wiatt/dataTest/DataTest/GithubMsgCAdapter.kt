package com.wiatt.dataTest.DataTest

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wiatt.dataTest.R
import com.wiatt.dataTest.data.GithubMsg
import com.wiatt.engine.database.entity.OwnerDb
import com.wiatt.engine.database.entity.RepoDb

class GithubMsgCAdapter(var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPEVIEW_OWNER = 1
        private const val TYPEVIEW_REPO = 2
    }

    private var githubMsgs: MutableList<GithubMsg> = mutableListOf()

    fun setData(data: MutableList<GithubMsg>) {
        githubMsgs.clear()
        githubMsgs.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPEVIEW_OWNER -> {
                val rootView = LayoutInflater.from(context).inflate(R.layout.fragment_github_msg_head, parent, false)
                OwnerViewHolder(rootView)
            }
            else -> {
                val rootView = LayoutInflater.from(context).inflate(R.layout.fragment_github_msg_content, parent, false)
                RepoViewHolder(rootView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = githubMsgs[position]
        when(holder.itemViewType) {
            TYPEVIEW_OWNER -> {
                val owner: OwnerDb = item.content as OwnerDb
                val ownerViewHolder = holder as OwnerViewHolder
                ownerViewHolder.tvNameValue.text = owner.name
                ownerViewHolder.tvRepoValue.text = owner.public_repos.toString()
                ownerViewHolder.tvLocationValue.text = owner.location
                ownerViewHolder.tvCompanyValue.text = owner.company
                ownerViewHolder.tvHtmlUrlValue.text = owner.html_url
            }
            else -> {
                val repo: RepoDb = item.content as RepoDb
                val repoViewHolder = holder as RepoViewHolder
                repoViewHolder.tvFullNameValue.text = repo.full_name
                repoViewHolder.tvGitUrlVaule.text = repo.git_url
                repoViewHolder.tvSshUrlVaule.text = repo.ssh_url
                repoViewHolder.tvCloneUrlVaule.text = repo.clone_url
                repoViewHolder.tvDescriptionVaule.text = repo.description
            }
        }
    }

    override fun getItemCount(): Int {
        return githubMsgs.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(githubMsgs[position].type) {
            GithubMsg.TYPE_OWNER -> {
                TYPEVIEW_OWNER
            }
            else -> {
                TYPEVIEW_REPO
            }
        }
    }

    class OwnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNameValue: TextView = itemView.findViewById(R.id.tvNameValue)
        val tvRepoValue: TextView = itemView.findViewById(R.id.tvRepoValue)
        val tvLocationValue: TextView = itemView.findViewById(R.id.tvLocationValue)
        val tvCompanyValue: TextView = itemView.findViewById(R.id.tvCompanyValue)
        val tvHtmlUrlValue: TextView = itemView.findViewById(R.id.tvHtmlUrlValue)
    }

    class RepoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvFullNameValue: TextView = itemView.findViewById(R.id.tvFullNameValue)
        val tvGitUrlVaule: TextView = itemView.findViewById(R.id.tvGitUrlVaule)
        val tvSshUrlVaule: TextView = itemView.findViewById(R.id.tvSshUrlVaule)
        val tvCloneUrlVaule: TextView = itemView.findViewById(R.id.tvCloneUrlVaule)
        val tvDescriptionVaule: TextView = itemView.findViewById(R.id.tvDescriptionVaule)
    }
}