document.addEventListener("DOMContentLoaded", () => {
    // 🔹 Get the search input and results container
    const searchInput = document.querySelector('#search-input');
    const resultsContainer = document.querySelector('#search-results');

    // 🔹 Listen for input events (user typing)
    searchInput.addEventListener("input", async () => {
        const query = searchInput.value.trim();

        // 🔹 If input is empty, hide results
        if (!query) {
            resultsContainer.innerHTML = "";
            resultsContainer.classList.add("d-none");
            return;
        }

        try {
            // 🔹 Fetch results from backend API
            const response = await fetch(`/api/search?query=${encodeURIComponent(query)}`);
            const posts = await response.json();

            // 🔹 Show "no results" if nothing returned
            if (posts.length === 0) {
                resultsContainer.innerHTML = "<div class='p-2 text-muted'>No results found</div>";
            } else {
                // 🔹 Map posts to HTML links
                resultsContainer.innerHTML = posts
                    .map(post => `
                        <a href="/posts/${post.id}" 
                           class="d-block px-3 py-2 text-dark text-decoration-none hover-bg-light">
                            <strong>${post.title}</strong><br>
                            <small class="text-muted">${post.communityName} • ${post.username}</small>
                        </a>
                    `)
                    .join("");
            }

            // 🔹 Make the results container visible
            resultsContainer.classList.remove("d-none");

        } catch (err) {
            console.error("Search error:", err);
        }
    });

    // 🔹 Hide results if clicking outside the input/results
    document.addEventListener("click", (e) => {
        if (!resultsContainer.contains(e.target) && e.target !== searchInput) {
            resultsContainer.classList.add("d-none");
        }
    });
});
