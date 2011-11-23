package com.lewish.asciiflow.server;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lewish.asciiflow.shared.Compressor;
import com.lewish.asciiflow.shared.OutputUtils;
import com.lewish.asciiflow.shared.State;
import com.lewish.asciiflow.shared.Compressor.Callback;

/**
 * This servlet is currently undocumented but should be used to fetch simple text
 * versions of a given diagram.
 * 
 * @author lewis
 */
public class EmbedServlet extends HttpServlet {

	private static final long serialVersionUID = 4874551688360608960L;

	// TODO: Inject.
	private final Compressor compressor = new ServerCompressor();

	public void doGet(HttpServletRequest req, final HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");

		long id = Long.valueOf(req.getParameter("id"));

		final State state = fetchState(id);
		compressor.uncompress(state, new Callback() {

			@Override
			public void onFinish(boolean success) {
				try {
					resp.getWriter().append(OutputUtils.toHtml(state.getCellStateMap()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private State fetchState(Long id) {
		PersistenceManager pm = Persistence.getManager();
		State state;
		try {
			state = pm.getObjectById(State.class, id);
		} catch (Exception e) {
			return null;
		} finally {
			pm.close();
		}
		return state;
	}
}