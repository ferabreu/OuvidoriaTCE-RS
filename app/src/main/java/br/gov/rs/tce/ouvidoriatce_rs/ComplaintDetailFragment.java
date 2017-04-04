package br.gov.rs.tce.ouvidoriatce_rs;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.gov.rs.tce.ouvidoriatce_rs.model.ComplaintListModel;

/**
 * A fragment representing a single ComplaintItem detail screen.
 * This fragment is either contained in a {@link ComplaintListActivity}
 * in two-pane mode (on tablets) or a {@link ComplaintDetailActivity}
 * on handsets.
 */
public class ComplaintDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The model content this fragment is presenting.
     */
    private ComplaintListModel.ComplaintItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ComplaintDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the model content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = ComplaintListModel.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.category);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.complaint_detail, container, false);

        // Show the model content as text in a TextView.
        if (mItem != null) {
            // TODO: here's something to change about the 2nd RecyclerView
            ((TextView) rootView.findViewById(R.id.complaint_detail)).setText(mItem.subjects.toString());
        }

        return rootView;
    }
}
