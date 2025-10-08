document.addEventListener('click', async (e)=>{
  const btn = e.target.closest('button.up,button.down');
  if(!btn) return;
  const id = btn.getAttribute('data-id');
  const isUp = btn.classList.contains('up');
  try {
    const resp = await fetch(`/api/post/${id}/${isUp?'upvote':'downvote'}`, {
      method: 'POST',
      headers: {'X-Requested-With':'fetch', 'Content-Type':'application/json'}
    });
    if(resp.status===401){ window.location = '/login'; return; }
    if(!resp.ok) throw new Error('vote failed');
    const data = await resp.json();
    document.querySelectorAll(`.score[data-id="${id}"]`).forEach(el=> el.textContent = data.score);
  } catch(err){
    alert('Could not vote. Are you logged in?');
  }
});