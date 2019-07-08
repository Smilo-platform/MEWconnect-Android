package com.myetherwallet.mewconnect.feature.main.fragment

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.myetherwallet.mewconnect.BuildConfig
import com.myetherwallet.mewconnect.R
import com.myetherwallet.mewconnect.core.di.ApplicationComponent
import com.myetherwallet.mewconnect.core.persist.prefenreces.PreferencesManager
import com.myetherwallet.mewconnect.core.ui.fragment.BaseDiFragment
import com.myetherwallet.mewconnect.core.utils.ApplicationUtils
import com.myetherwallet.mewconnect.core.utils.LaunchUtils
import com.myetherwallet.mewconnect.feature.backup.fragment.BackUpWalletFragment
import com.myetherwallet.mewconnect.feature.main.dialog.ResetWalletDialog
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.view_wallet_card.view.*
import javax.inject.Inject

/**
 * Created by BArtWell on 15.08.2018.
 */
class InfoFragment : BaseDiFragment(), Toolbar.OnMenuItemClickListener {

    companion object {
        fun newInstance() = InfoFragment()
    }

    @Inject
    lateinit var preferences: PreferencesManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        info_toolbar.inflateMenu(R.menu.close)
        info_toolbar.setOnMenuItemClickListener(this)

        info_contact.setOnClickListener { LaunchUtils.openMailApp(context, "info@smilo.io") }
//        info_user_guide.setOnClickListener { LaunchUtils.openWebSite(context, "https://kb.smilowallet.io/posts/mewconnect/mewconnect-user-guide/") }
//        info_knowledge_base.setOnClickListener { LaunchUtils.openWebSite(context, "https://Smilo-platform.github.io/knowledge-base/") }
        //TODO: revert when links are ok

        info_user_guide.visibility = View.GONE
        info_knowledge_base.visibility = View.GONE

        info_privacy_and_terms.setOnClickListener { LaunchUtils.openWebSite(context, "https://smilowallet.io/#/terms-and-conditions") }
        info_site.setOnClickListener { LaunchUtils.openWebSite(context, "https://smilowallet.io") }
        if (preferences.applicationPreferences.isBackedUp()) {
            info_view_recovery_phrase.setOnClickListener { addFragment(ViewRecoveryPhraseFragment.newInstance()) }
            info_view_recovery_phrase.setText(R.string.info_view_recovery_phrase)
        } else {
            info_view_recovery_phrase.setOnClickListener { addFragment(BackUpWalletFragment.newInstance()) }
            info_view_recovery_phrase.setText(R.string.info_back_up)
        }

        info_version.text = getString(R.string.info_version, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)

        info_reset_wallet.setOnClickListener {
            val dialog = ResetWalletDialog.newInstance()
            dialog.listener = {
                ApplicationUtils.removeAllData(context, preferences)
                replaceFragment(IntroFragment.newInstance())
            }
            dialog.show(childFragmentManager)
        }
    }

    override fun onMenuItemClick(menuItem: MenuItem): Boolean {
        close()
        return true
    }

    override fun inject(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }

    override fun layoutId() = R.layout.fragment_info
}