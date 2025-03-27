document.getElementById('scriptForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    let cloud = document.getElementById('cloud').value;
    let resourceType = document.getElementById('resourceType').value;
    let format = document.getElementById('format').value;
    let params = document.getElementById('params').value.split("\n")
                  .reduce((acc, line) => {
                      let [key, value] = line.split("=");
                      acc[key] = value;
                      return acc;
                  }, {});

    let queryString = new URLSearchParams({ cloud, resourceType, format, ...params }).toString();
    window.location.href = `/api/scripts/generate?${queryString}`;
});
