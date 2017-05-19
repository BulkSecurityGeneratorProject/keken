import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Commentaire } from './commentaire.model';
import { CommentaireService } from './commentaire.service';

@Component({
    selector: 'jhi-commentaire-detail',
    templateUrl: './commentaire-detail.component.html'
})
export class CommentaireDetailComponent implements OnInit, OnDestroy {

    commentaire: Commentaire;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private commentaireService: CommentaireService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCommentaires();
    }

    load(id) {
        this.commentaireService.find(id).subscribe((commentaire) => {
            this.commentaire = commentaire;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCommentaires() {
        this.eventSubscriber = this.eventManager.subscribe(
            'commentaireListModification',
            (response) => this.load(this.commentaire.id)
        );
    }
}
