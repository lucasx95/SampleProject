import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SampleOneToOne } from './sample-one-to-one.model';
import { SampleOneToOneService } from './sample-one-to-one.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sample-one-to-one',
    templateUrl: './sample-one-to-one.component.html'
})
export class SampleOneToOneComponent implements OnInit, OnDestroy {
sampleOneToOnes: SampleOneToOne[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private sampleOneToOneService: SampleOneToOneService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.sampleOneToOneService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.sampleOneToOnes = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.sampleOneToOneService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sampleOneToOnes = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSampleOneToOnes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SampleOneToOne) {
        return item.id;
    }
    registerChangeInSampleOneToOnes() {
        this.eventSubscriber = this.eventManager.subscribe('sampleOneToOneListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
