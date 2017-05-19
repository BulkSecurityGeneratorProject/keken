import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Biere } from './biere.model';
import { BiereService } from './biere.service';

@Component({
    selector: 'jhi-biere-detail',
    templateUrl: './biere-detail.component.html'
})
export class BiereDetailComponent implements OnInit, OnDestroy {

    biere: Biere;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private biereService: BiereService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBieres();
    }

    load(id) {
        this.biereService.find(id).subscribe((biere) => {
            this.biere = biere;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBieres() {
        this.eventSubscriber = this.eventManager.subscribe(
            'biereListModification',
            (response) => this.load(this.biere.id)
        );
    }
}
