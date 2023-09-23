package net.sn0wix_.worldofdragonsmod;

import net.fabricmc.api.ModInitializer;
import net.sn0wix_.worldofdragonsmod.blocks.ModBlocks;
import net.sn0wix_.worldofdragonsmod.effect.ModEffects;
import net.sn0wix_.worldofdragonsmod.entity.ModEntities;
import net.sn0wix_.worldofdragonsmod.item.ModItems;
import net.sn0wix_.worldofdragonsmod.particle.ModParticles;
import net.sn0wix_.worldofdragonsmod.sounds.ModSounds;
import net.sn0wix_.worldofdragonsmod.util.ModRegisteries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class WorldOfDragonsMain implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(WorldOfDragonsMain.MOD_ID);
	public static final String MOD_ID= "worldofdragonsmod";


	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEffects.registerEffects();
		ModSounds.registerModSounds();

		ModRegisteries.registerModStuffs();

		ModEntities.registerModEntities();

		ModParticles.registerParticles();

		GeckoLib.initialize();
	}

	public static String sendAiMessage(String message) {
		String url = "https://api.openai.com/v1/chat/completions";
		String apiKey = "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";
		String model = "gpt-3.5-turbo";

		try {
			// Create the HTTP POST request
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Authorization", "Bearer " + apiKey);
			con.setRequestProperty("Content-Type", "application/json");

			// Build the request body
			String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
			con.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
			writer.write(body);
			writer.flush();
			writer.close();

			// Get the response
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();//a;slkdfn;laskjdf;laskjdf Builder
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// returns the extracted contents of the response.
			return extractContentFromResponse(response.toString());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// This method extracts the response expected from chatgpt and returns it.
	public static String extractContentFromResponse(String response) {
		int startMarker = response.indexOf("content")+11; // Marker for where the content starts.
		int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
		return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
	}
}
